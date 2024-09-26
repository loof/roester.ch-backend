package ch.roester.variant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(VariantController.REQUEST_MAPPING)
@RestController
@Slf4j
public class VariantController {

    public static final String REQUEST_MAPPING = "/variants";
    private final VariantService variantService;

    @Autowired
    public VariantController(VariantService variantService) {
        this.variantService = variantService;
    }

    @Operation(summary = "Create a new variant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Variant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<VariantResponseDTO> create(@RequestBody @Valid VariantRequestDTO variantRequestDTO) {
        VariantResponseDTO createdVariant = variantService.save(variantRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVariant);
    }

    @Operation(summary = "Get a variant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variant found"),
            @ApiResponse(responseCode = "404", description = "Variant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VariantResponseDTO> findById(
            @Parameter(description = "ID of the variant to retrieve") @PathVariable("id") Integer id) {
        VariantResponseDTO variant = variantService.findById(id);
        return ResponseEntity.ok(variant);
    }

    @Operation(summary = "Get all variants with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variants retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VariantResponseDTO> variantPage = variantService.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("variants", variantPage.getContent());
        response.put("currentPage", variantPage.getNumber());
        response.put("totalItems", variantPage.getTotalElements());
        response.put("totalPages", variantPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing variant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Variant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Variant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<VariantResponseDTO> update(
            @Parameter(description = "ID of the variant to update") @PathVariable("id") Integer id,
            @RequestBody @Valid VariantRequestDTO variantRequestDTO) {
        VariantResponseDTO updatedVariant = variantService.update(id, variantRequestDTO);
        return ResponseEntity.ok(updatedVariant);
    }

    @Operation(summary = "Delete a variant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Variant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Variant not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the variant to delete") @PathVariable("id") Integer id) {
        variantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
