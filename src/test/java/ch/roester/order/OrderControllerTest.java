package ch.roester.order;

import ch.roester.product.Product;
import ch.roester.shipping_method.ShippingMethod;
import ch.roester.shipping_method.ShippingMethodRepository;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantRepository;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private MockMvc mockMvc;
    private OrderController orderController;
    private OrderService orderService;
    private VariantRepository variantRepository;
    private ShippingMethodRepository shippingMethodRepository;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        variantRepository = mock(VariantRepository.class);
        shippingMethodRepository = mock(ShippingMethodRepository.class);
        orderController = new OrderController(orderService, variantRepository, shippingMethodRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void create_ShouldReturnCreatedOrder() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        OrderResponseDTO responseDTO = new OrderResponseDTO();

        when(orderService.save(any(OrderRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<OrderResponseDTO> response = orderController.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void create_ShouldThrowBadRequest() {
        OrderRequestDTO requestDTO = new OrderRequestDTO();

        when(orderService.save(any(OrderRequestDTO.class))).thenThrow(new DataIntegrityViolationException(""));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.create(requestDTO));
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("Order could not be created", thrown.getReason());
    }

    @Test
    void findById_ShouldReturnOrder() {
        int orderId = 1;
        OrderResponseDTO responseDTO = new OrderResponseDTO();

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

    @Test
    void update_ShouldThrowNotFound() {
        int orderId = 1;
        OrderRequestDTO requestDTO = new OrderRequestDTO();

        when(orderService.update(orderId, requestDTO)).thenThrow(new EntityNotFoundException());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.update(requestDTO, orderId));
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Order not found", thrown.getReason());
    }

    @Test
    void update_ShouldThrowConflict() {
        int orderId = 1;
        OrderRequestDTO requestDTO = new OrderRequestDTO();

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

    @Test
    void calculateOrderTotal_ShouldReturnOrderTotal() {
        PositionRequestDTO position1 = new PositionRequestDTO();
        position1.setVariantId(1);
        position1.setAmount(2);

        PositionRequestDTO position2 = new PositionRequestDTO();
        position2.setVariantId(2);
        position2.setAmount(1);

        List<PositionRequestDTO> positions = Arrays.asList(position1, position2);

        Variant variant1 = new Variant();
        variant1.setWeightInGrams(new BigDecimal("500"));
        variant1.setStockMultiplier(new BigDecimal("1"));
        variant1.setSeparateShipment(false);
        variant1.setProduct(new Product());
        variant1.getProduct().setPricePerUnit(new BigDecimal("10"));

        Variant variant2 = new Variant();
        variant2.setWeightInGrams(new BigDecimal("1000"));
        variant2.setStockMultiplier(new BigDecimal("1"));
        variant2.setSeparateShipment(true);
        variant2.setProduct(new Product());
        variant2.getProduct().setPricePerUnit(new BigDecimal("20"));

        when(variantRepository.findById(1)).thenReturn(Optional.of(variant1));
        when(variantRepository.findById(2)).thenReturn(Optional.of(variant2));

        ShippingMethod shippingMethod1 = new ShippingMethod();
        shippingMethod1.setWeightInGramsLimit(1000);
        shippingMethod1.setPrice(new BigDecimal("5"));

        ShippingMethod shippingMethod2 = new ShippingMethod();
        shippingMethod2.setWeightInGramsLimit(2000);
        shippingMethod2.setPrice(new BigDecimal("10"));

        when(shippingMethodRepository.findAllOrderByWeightInGramsLimitAsc()).thenReturn(Arrays.asList(shippingMethod1, shippingMethod2));

        OrderResponseDTO response = orderController.calculateOrderTotal(positions);

        assertNotNull(response);
        assertEquals(new BigDecimal("60"), response.getOrderTotal());
        assertEquals(new BigDecimal("40"), response.getTotalCost());
        assertEquals(new BigDecimal("20"), response.getTotalShippingCost());
    }
}