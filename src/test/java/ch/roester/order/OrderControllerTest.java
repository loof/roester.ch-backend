package ch.roester.order;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    private OrderController orderController;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void create_ShouldReturnCreatedOrder() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        // Fill requestDTO with necessary data

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        // Fill responseDTO with expected data

        when(orderService.save(any(OrderRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<OrderResponseDTO> response = orderController.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void create_ShouldThrowBadRequest() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        // Fill requestDTO with invalid data to trigger DataIntegrityViolationException

        when(orderService.save(any(OrderRequestDTO.class))).thenThrow(new DataIntegrityViolationException(""));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.create(requestDTO));
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Order could not be created", thrown.getReason());
    }

    @Test
    void findById_ShouldReturnOrder() {
        int orderId = 1;
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        // Fill responseDTO with expected data

        when(orderService.findById(orderId)).thenReturn(responseDTO);

        ResponseEntity<OrderResponseDTO> response = orderController.findById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void findById_ShouldThrowNotFound() {
        int orderId = 1;

        when(orderService.findById(orderId)).thenThrow(new EntityNotFoundException());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.findById(orderId));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Order not found", thrown.getReason());
    }

    @Test
    void findAll_ShouldReturnOrders() {
        OrderResponseDTO order1 = new OrderResponseDTO();
        OrderResponseDTO order2 = new OrderResponseDTO();
        Page<OrderResponseDTO> orderPages = new PageImpl<>(Arrays.asList(order1, order2));

        // Mocking the service call
        when(orderService.findAll(any(Pageable.class))).thenReturn(orderPages);

        ResponseEntity<?> response = orderController.find(0, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, ((List<?>) responseBody.get("orders")).size());
        assertEquals(0, responseBody.get("currentPage"));
        assertEquals(2L, responseBody.get("totalItems"));
        assertEquals(1, responseBody.get("totalPages"));
    }

   /* @Test
    void update_ShouldReturnUpdatedOrder() {
        int orderId = 1;
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        // Fill requestDTO with necessary data

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        // Fill responseDTO with expected data

        when(orderService.update(orderId, requestDTO)).thenReturn(responseDTO);

        //ResponseEntity<OrderResponseDTO> response = orderController.update(requestDTO, orderId);

        //assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(responseDTO, response.getBody());
    }*/

    @Test
    void update_ShouldThrowNotFound() {
        int orderId = 1;
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        // Fill requestDTO with necessary data

        when(orderService.update(orderId, requestDTO)).thenThrow(new EntityNotFoundException());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.update(requestDTO, orderId));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Order not found", thrown.getReason());
    }

    @Test
    void update_ShouldThrowConflict() {
        int orderId = 1;
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        // Fill requestDTO with invalid data to trigger DataIntegrityViolationException

        when(orderService.update(orderId, requestDTO)).thenThrow(new DataIntegrityViolationException(""));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.update(requestDTO, orderId));
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals("Order could not be updated", thrown.getReason());
    }

    @Test
    void delete_ShouldReturnNoContent() {
        int orderId = 1;

        doNothing().when(orderService).deleteById(orderId);

        ResponseEntity<Void> response = orderController.delete(orderId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldThrowNotFound() {
        int orderId = 1;

        doThrow(new EmptyResultDataAccessException(1)).when(orderService).deleteById(orderId);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.delete(orderId));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Order not found", thrown.getReason());
    }
}
