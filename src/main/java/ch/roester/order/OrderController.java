package ch.roester.order;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RequestMapping(OrderController.REQUEST_MAPPING)
@RestController
@Slf4j
public class OrderController {

    public static final String REQUEST_MAPPING = "/orders";
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@Parameter(description = "Id of order to get") @PathVariable("id") Integer id) {
        try {
            OrderResponseDTO order = orderService.findById(id);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<OrderResponseDTO> orderPages = null;
        orderPages = orderService.findAll(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderPages.getContent());
        response.put("currentPage", orderPages.getNumber());
        response.put("totalItems", orderPages.getTotalElements());
        response.put("totalPages", orderPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<OrderRequestDTO> update(@Valid @RequestBody OrderRequestDTO orderRequestDto, @PathVariable("id") Integer id) {
        try {
            OrderRequestDTO updatedOrder = orderService.update(id, orderRequestDto);
            return ResponseEntity.ok(updatedOrder);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of order to delete") @PathVariable("id") Integer id) {
        try {
            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

}