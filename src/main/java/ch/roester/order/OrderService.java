package ch.roester.order;

import ch.roester.app_user.AppUser;
import ch.roester.carrier.Carrier;
import ch.roester.carrier.CarrierRepository;
import ch.roester.exception.FailedValidationException;
import ch.roester.shipment.*;
import ch.roester.shipping_method.ShippingMethod;
import ch.roester.shipping_method.ShippingMethodRepository;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final StatusRepository statusRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    private final CarrierRepository carrierRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, VariantRepository variantRepository, StatusRepository statusRepository, ShippingMethodRepository shippingMethodRepository, ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper, CarrierRepository carrierRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.variantRepository = variantRepository;
        this.statusRepository = statusRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
        this.carrierRepository = carrierRepository;
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


    public OrderResponseDTO save(OrderRequestDTO orderDto, Integer appUserId) {
        // Map OrderRequestDTO to Order entity
        Order order = orderMapper.fromRequestDTO(orderDto);
        AppUser appUser = new AppUser();
        appUser.setId(appUserId);
        order.setAppUser(appUser);

        processOrderPositions(order);

        Optional<Status> existingStatus = statusRepository.getFirstByName(order.getStatus().getName());
        if (existingStatus.isPresent()) {
            order.setStatus(existingStatus.get());
        } else {
            statusRepository.save(order.getStatus());
        }

        // Persist the order and its associated positions
        Order savedOrder = orderRepository.save(order); // This will cascade to positions if configured
        OrderResponseDTO calculatedShipments = calculateShipmentsFromPositions(orderDto.getPositions(), orderDto.getCarrierId());
        List<ShipmentResponseDTO> calculatedShipmentDTOs = calculatedShipments.getShipments();
        List<Shipment> shipmentsToSave = shipmentMapper.convertShipmentDTOsToEntities(calculatedShipmentDTOs, savedOrder.getId());
        List<Shipment> savedShipments = shipmentRepository.saveAll(shipmentsToSave);
        savedOrder.setShipments(savedShipments);
        OrderResponseDTO response = orderMapper.toResponseDTO(savedOrder);
        response.setOrderTotal(calculatedShipments.getOrderTotal());
        response.setTotalShippingCost(calculatedShipments.getTotalShippingCost());
        response.setTotalCost(calculatedShipments.getTotalCost());
        response.setIsPickup(orderDto.getIsPickup());
        response.setCarrierId(orderDto.getCarrierId());
        return response;
    }


    public OrderResponseDTO calculateShipmentsFromPositions(List<PositionRequestDTO> positions, Integer carrierId) {
        Carrier carrier = carrierRepository.findById(carrierId).orElseThrow(() -> new EntityNotFoundException("Carrier not found"));
        List<ShippingMethod> shippingMethods = carrier.getShippingMethods();
        List<Variant> variants = new ArrayList<>();
        BigDecimal totalVariantCost = BigDecimal.ZERO;

        for (PositionRequestDTO position : positions) {
            Variant variant = variantRepository.findById(position.getVariantId())
                    .orElseThrow(() -> new EntityNotFoundException("Variant not found"));
            for (int i = 0; i < position.getAmount(); i++) {
                variants.add(variant);
                totalVariantCost = totalVariantCost.add(variant.getStockMultiplier().multiply(variant.getProduct().getPricePerUnit()));
            }
        }

        List<Shipment> returnedShipments = calculateShipments(variants, shippingMethods);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setPositions(positions);
        orderResponseDTO.setShipments(shipmentMapper.toResponseDTO(returnedShipments));
        BigDecimal totalShippingCost = returnedShipments.stream()
                .map(Shipment::getShipmentCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderResponseDTO.setTotalShippingCost(totalShippingCost);
        orderResponseDTO.setNumberOfParcels(returnedShipments.size());
        orderResponseDTO.setOrderTotal(totalVariantCost);
        orderResponseDTO.setTotalCost(totalVariantCost.add(totalShippingCost));

        return orderResponseDTO;
    }

    /**
     * Set the order reference and manage variants in each position
     * @param order Order to get positions from
     */
    private void processOrderPositions(Order order) {
        if (order.getPositions() != null) {
            for (Position position : order.getPositions()) {
                Variant variant = variantRepository.findById(position.getVariant().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Variant not found"));
                position.setVariant(variant);
                position.setOrder(order);
            }
        }
    }

    private void addShipment(List<Shipment> shipments, List<ShippingMethod> shippingMethods, BigDecimal weight) {
        ShippingMethod method = findCheapestShippingMethod(shippingMethods, weight);
        Shipment shipment = new Shipment();
        shipment.setShippingMethod(method);
        shipment.setShipmentCost(method.getPrice());
        shipments.add(shipment);
    }

    private List<Shipment> calculateShipments(List<Variant> variants, List<ShippingMethod> shippingMethods) {
        List<Shipment> shipments = new ArrayList<>();
        BigDecimal currentWeight = BigDecimal.ZERO;
        BigDecimal maxWeightLimit = shippingMethods.stream()
                .map(ShippingMethod::getWeightInGramsLimit)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        for (Variant variant : variants) {
            BigDecimal variantWeight = variant.getWeightInGrams();
            if (variant.isSeparateShipment()) {
                addShipment(shipments, shippingMethods, variantWeight);
            } else {
                if (currentWeight.add(variantWeight).compareTo(maxWeightLimit) > 0) {
                    addShipment(shipments, shippingMethods, currentWeight);
                    currentWeight = BigDecimal.ZERO;
                }
                currentWeight = currentWeight.add(variantWeight);
            }
        }

        if (currentWeight.compareTo(BigDecimal.ZERO) > 0) {
            addShipment(shipments, shippingMethods, currentWeight);
        }

        return shipments;
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