package ch.roester.order;

import ch.roester.exception.FailedValidationException;
import ch.roester.shipping_method.ShippingMethod;
import ch.roester.shipping_method.ShippingMethodRepository;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalShippingCost = BigDecimal.ZERO;
        BigDecimal weightSum = BigDecimal.ZERO;
        BigDecimal totalWidth = BigDecimal.ZERO;
        BigDecimal totalHeight = BigDecimal.ZERO;
        BigDecimal totalDepth = BigDecimal.ZERO;
        int numberOfParcels = 0;

        for (PositionRequestDTO position : positions) {
            Variant variant = variantRepository.findById(position.getVariantId()).orElseThrow(() -> new RuntimeException("Variant not found"));
            BigDecimal positionCost = variant.getStockMultiplier()
                    .multiply(variant.getProduct().getPricePerUnit())
                    .multiply(new BigDecimal(position.getAmount()));
            totalCost = totalCost.add(positionCost);

            if (variant.isSeparateShipment()) {
                BigDecimal shippingCost = calculateShippingCost(variant.getWeightInGrams(), variant.getWidthInCm(), variant.getHeightInCm(), variant.getDepthInCm());
                totalShippingCost = totalShippingCost.add(shippingCost.multiply(new BigDecimal(position.getAmount())));
                numberOfParcels += position.getAmount();
            } else {
                weightSum = weightSum.add(variant.getWeightInGrams().multiply(new BigDecimal(position.getAmount())));
                totalWidth = totalWidth.add(variant.getWidthInCm().multiply(new BigDecimal(position.getAmount())));
                totalHeight = totalHeight.add(variant.getHeightInCm().multiply(new BigDecimal(position.getAmount())));
                totalDepth = totalDepth.add(variant.getDepthInCm().multiply(new BigDecimal(position.getAmount())));
            }
        }

        if (weightSum.compareTo(BigDecimal.ZERO) > 0) {
            totalShippingCost = totalShippingCost.add(calculateShippingCost(weightSum, totalWidth, totalHeight, totalDepth));
            numberOfParcels++;
        }

        BigDecimal orderTotal = totalCost.add(totalShippingCost);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setTotalCost(totalCost);
        response.setTotalShippingCost(totalShippingCost);
        response.setOrderTotal(orderTotal);
        response.setNumberOfParcels(numberOfParcels); // Set the number of parcels

        return response;
    }

    private BigDecimal calculateShippingCost(BigDecimal weight, BigDecimal width, BigDecimal height, BigDecimal depth) {
        List<ShippingMethod> shippingMethods = shippingMethodRepository.findAllByOrderByWeightInGramsLimitAsc();
        for (ShippingMethod method : shippingMethods) {
            if (weight.compareTo(new BigDecimal(method.getWeightInGramsLimit())) <= 0 &&
                    width.compareTo(method.getInnerWidthInCm()) <= 0 &&
                    height.compareTo(method.getInnerHeightInCm()) <= 0 &&
                    depth.compareTo(method.getInnerDepthInCm()) <= 0) {
                return method.getPrice();
            }
        }
        throw new FailedValidationException(Map.of("shippingMethod", List.of("No suitable shipping method found for the given weight and dimensions.")));
    }

    public void deleteById(Integer id) {
        orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        orderRepository.deleteById(id);
    }
}