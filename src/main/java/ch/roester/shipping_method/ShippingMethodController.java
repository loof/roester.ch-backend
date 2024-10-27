package ch.roester.shipping_method;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a new shipping method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Shipping method to be created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShippingMethodRequestDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"name\": \"Standard Shipping\", \"price\": 5.00, \"estimatedDeliveryTime\": \"3-5 business days\", \"description\": \"Standard shipping method with delivery in 3-5 business days\", \"weightInGramsLimit\": 1000 }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Shipping method created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<ShippingMethodResponseDTO> create(@RequestBody @Valid ShippingMethodRequestDTO shippingMethodRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(shippingMethodService.save(shippingMethodRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping Method could not be created");
        }
    }

    @Operation(summary = "Get a shipping method by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Shipping method found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShippingMethodResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = "{ \"id\": 1, \"name\": \"Standard Shipping\", \"price\": 5.00, \"estimatedDeliveryTime\": \"3-5 business days\", \"description\": \"Standard shipping method with delivery in 3-5 business days\", \"weightInGramsLimit\": 1000 }"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Shipping method not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ShippingMethodResponseDTO> findById(@Parameter(description = "Id of shipping method to get") @PathVariable("id") Integer id) {
        try {
            ShippingMethodResponseDTO shippingMethod = shippingMethodService.findById(id);
            return ResponseEntity.ok(shippingMethod);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping Method not found");
        }
    }

    @Operation(summary = "Get all shipping methods",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of shipping methods",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShippingMethodResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = "[{ \"id\": 1, \"name\": \"Standard Shipping\", \"price\": 5.00, \"estimatedDeliveryTime\": \"3-5 business days\", \"description\": \"Standard shipping method with delivery in 3-5 business days\", \"weightInGramsLimit\": 1000 }, { \"id\": 2, \"name\": \"Express Shipping\", \"price\": 10.00, \"estimatedDeliveryTime\": \"1-2 business days\", \"description\": \"Express shipping method with delivery in 1-2 business days\", \"weightInGramsLimit\": 2000 }]"
                                    )
                            )
                    )
            }
    )
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

    @Operation(summary = "Update a shipping method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Shipping method to be updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShippingMethodRequestDTO.class),
                            examples = @ExampleObject(
                                    value = "{ \"name\": \"Standard Shipping\", \"price\": 5.00, \"estimatedDeliveryTime\": \"3-5 business days\", \"description\": \"Standard shipping method with delivery in 3-5 business days\", \"weightInGramsLimit\": 1000 }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Shipping method updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Shipping method not found"),
                    @ApiResponse(responseCode = "409", description = "Conflict in updating shipping method")
            }
    )
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

    @Operation(summary = "Delete a shipping method",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Shipping method deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Shipping method not found")
            }
    )
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