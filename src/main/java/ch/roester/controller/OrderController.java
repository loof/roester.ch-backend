package ch.roester.controller;

import ch.roester.dto.OrderDto;
import ch.roester.mapper.OrderMapper;
import ch.roester.order.Order;
import ch.roester.service.OrderService;
import com.sun.tools.javac.util.DefinedBy.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/order")
@RestController
@Slf4j
@Api("order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated OrderDto orderDto) {
        orderService.save(orderDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable("id") Integer id) {
        OrderDto order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Optional.ofNullable(orderService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<OrderDto>> pageQuery(OrderDto orderDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderDto> orderPage = orderService.findByCondition(orderDto, pageable);
        return ResponseEntity.ok(orderPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated OrderDto orderDto, @PathVariable("id") Integer id) {
        orderService.update(orderDto, id);
        return ResponseEntity.ok().build();
    }
}