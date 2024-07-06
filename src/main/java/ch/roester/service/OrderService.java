package ch.roester.service;

import ch.roester.dto.OrderDto;
import ch.roester.mapper.OrderMapper;
import ch.roester.order.Order;
import ch.roester.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository repository, OrderMapper orderMapper) {
        this.repository = repository;
        this.orderMapper = orderMapper;
    }

    public OrderDto save(OrderDto orderDto) {
        Order entity = orderMapper.toEntity(orderDto);
        return orderMapper.toDto(repository.save(entity));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public OrderDto findById(Integer id) {
        return orderMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<OrderDto> findByCondition(OrderDto orderDto, Pageable pageable) {
        Page<Order> entityPage = repository.findAll(pageable);
        List<Order> entities = entityPage.getContent();
        return new PageImpl<>(orderMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public OrderDto update(OrderDto orderDto, Integer id) {
        OrderDto data = findById(id);
        Order entity = orderMapper.toEntity(orderDto);
        BeanUtil.copyProperties(data, entity);
        return save(orderMapper.toDto(entity));
    }
}