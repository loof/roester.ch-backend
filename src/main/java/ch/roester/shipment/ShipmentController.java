package ch.roester.shipment;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(ShipmentController.REQUEST_MAPPING)
@RestController
@Slf4j
public class ShipmentController {

    public static final String REQUEST_MAPPING = "/shipments";
    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> create(@RequestBody @Valid ShipmentRequestDTO shipmentRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.save(shipmentRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipment could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> findById(@Parameter(description = "Id of shipment to get") @PathVariable("id") Integer id) {
        try {
            ShipmentResponseDTO shipment = shipmentService.findById(id);
            return ResponseEntity.ok(shipment);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<ShipmentResponseDTO> shipmentPages = shipmentService.findAll(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("shipments", shipmentPages.getContent());
        response.put("currentPage", shipmentPages.getNumber());
        response.put("totalItems", shipmentPages.getTotalElements());
        response.put("totalPages", shipmentPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ShipmentResponseDTO> update(@Valid @RequestBody ShipmentRequestDTO shipmentRequestDto, @PathVariable("id") Integer id) {
        try {
            ShipmentResponseDTO updatedShipment = shipmentService.update(id, shipmentRequestDto);
            return ResponseEntity.ok(updatedShipment);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Shipment could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of shipment to delete") @PathVariable("id") Integer id) {
        try {
            shipmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found");
        }
    }
}
