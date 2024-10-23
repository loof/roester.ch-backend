package ch.roester.order;

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

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, VariantRepository variantRepository, PositionRepository positionRepository, StatusRepository statusRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.variantRepository = variantRepository;
        this.positionRepository = positionRepository;
        this.statusRepository = statusRepository;
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





        // Persist the order and its associated positions
        Order savedOrder = orderRepository.save(order); // This will cascade to positions if configured

        return orderMapper.toResponseDTO(savedOrder);
    }




    public void deleteById(Integer id) {
        orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        orderRepository.deleteById(id);
    }
}