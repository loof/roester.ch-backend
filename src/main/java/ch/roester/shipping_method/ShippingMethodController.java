package ch.roester.shipping_method;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(ShippingMethodController.REQUEST_MAPPING)
@RestController
@Slf4j
public class ShippingMethodController {

    public static final String REQUEST_MAPPING = "/shipping-methods";
    private final ShippingMethodService shippingMethodService;

    @Autowired
    public ShippingMethodController(ShippingMethodService shippingMethodService) {
        this.shippingMethodService = shippingMethodService;
    }

    @PostMapping
    public ResponseEntity<ShippingMethodResponseDTO> create(@RequestBody @Valid ShippingMethodRequestDTO shippingMethodRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(shippingMethodService.save(shippingMethodRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping Method could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingMethodResponseDTO> findById(@Parameter(description = "Id of shipping method to get") @PathVariable("id") Integer id) {
        try {
            ShippingMethodResponseDTO shippingMethod = shippingMethodService.findById(id);
            return ResponseEntity.ok(shippingMethod);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping Method not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "name") String sortBy) {
        Page<ShippingMethodResponseDTO> shippingMethods = shippingMethodService.findAll(PageRequest.of(page, size).withSort(Sort.by(sortBy)));

        Map<String, Object> response = new HashMap<>();
        response.put("shippingMethods", shippingMethods.getContent());
        response.put("currentPage", shippingMethods.getNumber());
        response.put("totalItems", shippingMethods.getTotalElements());
        response.put("totalPages", shippingMethods.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShippingMethodRequestDTO> update(@RequestBody @Valid ShippingMethodRequestDTO shippingMethodRequestDTO, @PathVariable("id") Integer id) {
        try {
            ShippingMethodRequestDTO updatedShippingMethod = shippingMethodService.update(id, shippingMethodRequestDTO);
            return ResponseEntity.ok(updatedShippingMethod);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Shipping Method could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping Method not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of shipping method to delete") @PathVariable("id") Integer id) {
        try {
            shippingMethodService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping Method not found");
        }
    }
}
