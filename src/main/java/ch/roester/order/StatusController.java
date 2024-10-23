package ch.roester.order;

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

@RequestMapping(StatusController.REQUEST_MAPPING)
@RestController
@Slf4j
public class StatusController {

    public static final String REQUEST_MAPPING = "/statuses";
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @Operation(
            summary = "Create a new status",
            description = "This endpoint creates a new status in the system.",
            tags = {"Status Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Status created successfully",
                    content = @Content(schema = @Schema(implementation = StatusResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input data")
    })
    @PostMapping
    public ResponseEntity<StatusResponseDTO> create(
            @Parameter(description = "Status object that needs to be created", required = true)
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Status object that needs to be created",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusRequestDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"Pending\"\n" +
                                    "}")
                    )
            )
            StatusRequestDTO statusRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.create(statusRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status could not be created");
        }
    }

    @Operation(
            summary = "Update a status",
            description = "Update the details of an existing status.",
            tags = {"Status Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = StatusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found: Status not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Invalid data provided")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> update(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Status object with updated values",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusRequestDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"Shipped\"\n" +
                                    "}")
                    )
            ) StatusRequestDTO statusRequestDto,
            @Parameter(description = "ID of the status to update", required = true) @PathVariable("id") Integer id) {
        try {
            StatusResponseDTO updatedStatus = statusService.update(id, statusRequestDto);
            return ResponseEntity.ok(updatedStatus);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Status could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found");
        }
    }

    @Operation(
            summary = "Retrieve a status by ID",
            description = "Fetches the details of a status by its ID.",
            tags = {"Status Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StatusResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found: Status not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> findById(@Parameter(description = "Id of status to get") @PathVariable("id") Integer id) {
        try {
            StatusResponseDTO statusResponseDTO = statusService.findById(id);
            return ResponseEntity.ok(statusResponseDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found");
        }
    }

    @Operation(
            summary = "Delete a status",
            description = "Deletes a status from the system.",
            tags = {"Status Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found: Status not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the status to delete", required = true) @PathVariable("id") Integer id) {
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve all statuses",
            description = "Fetches a paginated list of all statuses.",
            tags = {"Status Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of statuses retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponseDTO.class))),
    })
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        // Set up pagination and sorting
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<StatusResponseDTO> statusPages;

        statusPages = statusService.findAll(paging);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("statuses", statusPages.getContent());
        response.put("currentPage", statusPages.getNumber());
        response.put("totalItems", statusPages.getTotalElements());
        response.put("totalPages", statusPages.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
