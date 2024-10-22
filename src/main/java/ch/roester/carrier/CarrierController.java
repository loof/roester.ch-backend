package ch.roester.carrier;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

@RequestMapping(CarrierController.REQUEST_MAPPING)
@RestController
@Slf4j
public class CarrierController {

    public static final String REQUEST_MAPPING = "/carriers";
    private final CarrierService carrierService;

    @Autowired
    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @Operation(
            summary = "Create a new carrier",
            description = "This endpoint creates a new carrier in the system.",
            tags = {"Carrier Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrier created successfully",
                    content = @Content(schema = @Schema(implementation = CarrierResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CarrierResponseDTO> create(
            @Parameter(description = "Carrier object that needs to be created", required = true)
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Carrier object that needs to be created",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarrierRequestDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"DHL Express\",\n" +
                                    "  \"contactNumber\": \"+49 987 654 321\",\n" +
                                    "  \"email\": \"contact@dhl-express.com\",\n" +
                                    "  \"website\": \"https://www.dhl-express.com\"\n" +
                                    "}")
                    )
            )
            CarrierRequestDTO carrierRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(carrierService.save(carrierRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carrier could not be created");
        }
    }

    @Operation(
            summary = "Update a carrier",
            description = "Update the details of an existing carrier.",
            tags = {"Carrier Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrier updated successfully",
                    content = @Content(schema = @Schema(implementation = CarrierResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found: Carrier not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Invalid data provided")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CarrierResponseDTO> update(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Carrier object with updated values",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarrierRequestDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"DHL Express\",\n" +
                                    "  \"contactNumber\": \"+49 987 654 321\",\n" +
                                    "  \"email\": \"contact@dhl-express.com\",\n" +
                                    "  \"website\": \"https://www.dhl-express.com\"\n" +
                                    "}")
                    )
            ) CarrierRequestDTO carrierRequestDto,
            @Parameter(description = "ID of the carrier to update", required = true) @PathVariable("id") Integer id) {
        try {
            CarrierResponseDTO updatedCarrier = carrierService.update(id, carrierRequestDto);
            return ResponseEntity.ok(updatedCarrier);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Carrier could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrier not found");
        }
    }

    @Operation(
            summary = "Retrieve a carrier by ID",
            description = "Fetches the details of a carrier by its ID.",
            tags = {"Carrier Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrier retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CarrierResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found: Carrier not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarrierResponseDTO> findById(@Parameter(description = "Id of carrier to get") @PathVariable("id") Integer id) {
        try {
            CarrierResponseDTO carrierResponseDTO = carrierService.findById(id);
            return ResponseEntity.ok(carrierResponseDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @Operation(
            summary = "Delete a carrier",
            description = "Deletes a carrier from the system.",
            tags = {"Carrier Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrier deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found: Carrier not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the carrier to delete", required = true) @PathVariable("id") Integer id) {
        carrierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve all carriers",
            description = "Fetches a paginated list of all carriers.",
            tags = {"Carrier Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of carriers retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarrierResponseDTO.class))),
    })
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        // Set up pagination and sorting
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<CarrierResponseDTO> carrierPages;

        carrierPages = carrierService.findAll(paging);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("carriers", carrierPages.getContent());
        response.put("currentPage", carrierPages.getNumber());
        response.put("totalItems", carrierPages.getTotalElements());
        response.put("totalPages", carrierPages.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
