package ch.roester.order;

import ch.roester.exception.FailedValidationException;
import ch.roester.shipment.Shipment;
import ch.roester.shipping_method.ShippingMethod;
import ch.roester.shipping_method.ShippingMethodRepository;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantRepository;
import jakarta.persistence.EntityNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final VariantRepository variantRepository;
    private final PositionRepository positionRepository;
    private final StatusRepository statusRepository;
    private final ShippingMethodRepository shippingMethodRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, VariantRepository variantRepository, PositionRepository positionRepository, StatusRepository statusRepository, ShippingMethodRepository shippingMethodRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.variantRepository = variantRepository;
        this.positionRepository = positionRepository;
        this.statusRepository = statusRepository;
        this.shippingMethodRepository = shippingMethodRepository;
    }

    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        return orderMapper.toResponseDTO(orderRepository.findAll(pageable));
    }

    public OrderResponseDTO findById(Integer id) {
        return orderMapper.toResponseDTO(orderRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public OrderResponseDTO update(Integer id, OrderRequestDTO updatingOrder) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(updatingOrder, existingOrder);
        return orderMapper.toResponseDTO(orderRepository.save(existingOrder.get()));
    }

    public OrderResponseDTO save(OrderRequestDTO orderDto) {
        // Map OrderRequestDTO to Order entity
        Order order = orderMapper.fromRequestDTO(orderDto);

        // Set the order reference and manage variants in each position
        if (order.getPositions() != null) {
            for (Position position : order.getPositions()) {
                // Fetch the variant by ID to ensure it's managed
                Variant variant = variantRepository.findById(position.getVariant().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Variant not found"));

                position.setVariant(variant); // Set the managed variant
                position.setOrder(order); // Set the order reference in the position
            }
        }

        Optional<Status> existingStatus = statusRepository.getFirstByName(order.getStatus().getName());
        if (existingStatus.isPresent()) {
            order.setStatus(existingStatus.get());
        } else {
            statusRepository.save(order.getStatus());
        }

        // Persist the order and its associated positions
        Order savedOrder = orderRepository.save(order); // This will cascade to positions if configured

        return orderMapper.toResponseDTO(savedOrder);
    }

    public OrderResponseDTO calculateOrderTotal(List<PositionRequestDTO> positions) {
        List<ShippingMethod> shippingMethods = shippingMethodRepository.findAll();
        List<Variant> variants = new ArrayList<>();

        for (PositionRequestDTO position : positions) {
            Variant variant = variantRepository.findById(position.getVariantId())
                    .orElseThrow(() -> new EntityNotFoundException("Variant not found"));
            for (int i = 0; i < position.getAmount(); i++) {
                variants.add(variant);
            }
        }

        BigDecimal lowestCost = calculateLowestShippingCost(variants, shippingMethods);
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setTotalShippingCost(lowestCost);
        responseDTO.setNumberOfParcels(calculateNumberOfParcels(variants, shippingMethods));
        return responseDTO;
    }

    private BigDecimal calculateLowestShippingCost(List<Variant> variants, List<ShippingMethod> shippingMethods) {
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal currentWeight = BigDecimal.ZERO;
        BigDecimal maxWeightLimit = shippingMethods.stream()
                .map(ShippingMethod::getWeightInGramsLimit)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        for (Variant variant : variants) {
            BigDecimal variantWeight = variant.getWeightInGrams();
            if (variant.isSeparateShipment()) {
                totalCost = totalCost.add(findCheapestShippingMethod(shippingMethods, variantWeight).getPrice());
            } else {
                if (currentWeight.add(variantWeight).compareTo(maxWeightLimit) > 0) {
                    totalCost = totalCost.add(findCheapestShippingMethod(shippingMethods, currentWeight).getPrice());
                    currentWeight = BigDecimal.ZERO;
                }
                currentWeight = currentWeight.add(variantWeight);
            }
        }

        if (currentWeight.compareTo(BigDecimal.ZERO) > 0) {
            totalCost = totalCost.add(findCheapestShippingMethod(shippingMethods, currentWeight).getPrice());
        }

        return totalCost;
    }

    private int calculateNumberOfParcels(List<Variant> variants, List<ShippingMethod> shippingMethods) {
        int numberOfParcels = 0;
        BigDecimal currentWeight = BigDecimal.ZERO;
        BigDecimal maxWeightLimit = shippingMethods.stream()
                .map(ShippingMethod::getWeightInGramsLimit)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        for (Variant variant : variants) {
            BigDecimal variantWeight = variant.getWeightInGrams();
            if (variant.isSeparateShipment()) {
                numberOfParcels++;
            } else {
                if (currentWeight.add(variantWeight).compareTo(maxWeightLimit) > 0) {
                    numberOfParcels++;
                    currentWeight = BigDecimal.ZERO;
                }
                currentWeight = currentWeight.add(variantWeight);
            }
        }

        if (currentWeight.compareTo(BigDecimal.ZERO) > 0) {
            numberOfParcels++;
        }

        return numberOfParcels;
    }

    private ShippingMethod findCheapestShippingMethod(List<ShippingMethod> shippingMethods, BigDecimal weight) {
        return shippingMethods.stream()
                .filter(method -> method.getWeightInGramsLimit().compareTo(weight) >= 0)
                .min(Comparator.comparing(ShippingMethod::getPrice))
                .orElseThrow(() -> new EntityNotFoundException("No suitable shipping methods available"));
    }

    public void deleteById(Integer id) {
        orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        orderRepository.deleteById(id);
    }
}