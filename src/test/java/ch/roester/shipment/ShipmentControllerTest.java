package ch.roester.shipment;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;

    @InjectMocks
    private ShipmentController shipmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedShipment() {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO(); // Fill this with valid data
        ShipmentResponseDTO responseDto = new ShipmentResponseDTO(); // Fill this with expected response data

        when(shipmentService.save(any(ShipmentRequestDTO.class))).thenReturn(responseDto);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.create(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void create_ShouldThrowBadRequest_WhenDataIntegrityViolationOccurs() {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO(); // Fill this with valid data
        when(shipmentService.save(any(ShipmentRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            shipmentController.create(requestDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Shipment could not be created", exception.getReason());
    }

    @Test
    void findById_ShouldReturnShipment_WhenFound() {
        ShipmentResponseDTO responseDto = new ShipmentResponseDTO(); // Fill this with expected response data
        when(shipmentService.findById(anyInt())).thenReturn(responseDto);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void findById_ShouldThrowNotFound_WhenShipmentNotFound() {
        when(shipmentService.findById(anyInt())).thenThrow(EntityNotFoundException.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            shipmentController.findById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Shipment not found", exception.getReason());
    }

    @Test
    void find_ShouldReturnShipments() {
        // Create mock shipment DTOs
        ShipmentResponseDTO shipment1 = new ShipmentResponseDTO(); // Fill this with test data if necessary
        ShipmentResponseDTO shipment2 = new ShipmentResponseDTO(); // Fill this with test data if necessary
        Page<ShipmentResponseDTO> shipmentPage = new PageImpl<>(Arrays.asList(shipment1, shipment2));

        // Mock the shipmentService to return the mocked page
        when(shipmentService.findAll(any(Pageable.class))).thenReturn(shipmentPage);

        // Call the controller method
        ResponseEntity<?> response = shipmentController.find(0, 5);

        // Assert the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Get the response body
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        // Check that the response body is not null
        assertNotNull(responseBody);

        // Assert the size of the shipments list
        assertNotNull(responseBody.get("shipments"));
        assertEquals(2, ((Collection<?>) responseBody.get("shipments")).size()); // Check the size of the shipments list
        assertEquals(0, responseBody.get("currentPage"));
        assertEquals(2L, responseBody.get("totalItems"));
        assertEquals(1, responseBody.get("totalPages"));
    }


    @Test
    void update_ShouldReturnUpdatedShipment() {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO(); // Fill this with valid data
        ShipmentResponseDTO responseDto = new ShipmentResponseDTO(); // Fill this with expected response data

        when(shipmentService.update(anyInt(), any(ShipmentRequestDTO.class))).thenReturn(responseDto);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.update(requestDto, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void update_ShouldThrowNotFound_WhenShipmentNotFound() {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO(); // Fill this with valid data
        when(shipmentService.update(anyInt(), any(ShipmentRequestDTO.class))).thenThrow(EntityNotFoundException.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            shipmentController.update(requestDto, 1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Shipment not found", exception.getReason());
    }

    @Test
    void update_ShouldThrowConflict_WhenDataIntegrityViolationOccurs() {
        ShipmentRequestDTO requestDto = new ShipmentRequestDTO(); // Fill this with valid data
        when(shipmentService.update(anyInt(), any(ShipmentRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            shipmentController.update(requestDto, 1);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Shipment could not be updated", exception.getReason());
    }

    @Test
    void delete_ShouldReturnNoContent_WhenShipmentDeleted() {
        doNothing().when(shipmentService).deleteById(anyInt());

        ResponseEntity<Void> response = shipmentController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldThrowNotFound_WhenShipmentNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(shipmentService).deleteById(anyInt());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            shipmentController.delete(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Shipment not found", exception.getReason());
    }
}
