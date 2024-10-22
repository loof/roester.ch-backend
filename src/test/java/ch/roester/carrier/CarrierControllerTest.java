package ch.roester.carrier;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CarrierControllerTest {

    @InjectMocks
    private CarrierController carrierController;

    @Mock
    private CarrierService carrierService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void createCarrier_shouldReturnCreatedCarrier() throws Exception {
        CarrierRequestDTO carrierRequestDTO = new CarrierRequestDTO();
        carrierRequestDTO.setName("DHL Express");
        carrierRequestDTO.setContactNumber("+49 987 654 321");
        carrierRequestDTO.setEmail("contact@dhl-express.com");
        carrierRequestDTO.setWebsite("https://www.dhl-express.com");

        CarrierResponseDTO carrierResponseDTO = new CarrierResponseDTO();
        carrierResponseDTO.setId(1);
        carrierResponseDTO.setName("DHL Express");
        carrierResponseDTO.setContactNumber("+49 987 654 321");
        carrierResponseDTO.setEmail("contact@dhl-express.com");
        carrierResponseDTO.setWebsite("https://www.dhl-express.com");

        when(carrierService.save(any(CarrierRequestDTO.class))).thenReturn(carrierResponseDTO);

        ResponseEntity<CarrierResponseDTO> response = carrierController.create(carrierRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(carrierResponseDTO);
        verify(carrierService, times(1)).save(carrierRequestDTO);
    }

    @Test
    void updateCarrier_shouldReturnUpdatedCarrier() throws Exception {
        int carrierId = 1;

        CarrierRequestDTO carrierRequestDTO = new CarrierRequestDTO();
        carrierRequestDTO.setName("DHL Express Updated");
        carrierRequestDTO.setContactNumber("+49 987 654 321");
        carrierRequestDTO.setEmail("contact@dhl-express.com");
        carrierRequestDTO.setWebsite("https://www.dhl-express.com");

        CarrierResponseDTO carrierResponseDTO = new CarrierResponseDTO();
        carrierResponseDTO.setId(carrierId);
        carrierResponseDTO.setName("DHL Express Updated");
        carrierResponseDTO.setContactNumber("+49 987 654 321");
        carrierResponseDTO.setEmail("contact@dhl-express.com");
        carrierResponseDTO.setWebsite("https://www.dhl-express.com");

        when(carrierService.update(eq(carrierId), any(CarrierRequestDTO.class))).thenReturn(carrierResponseDTO);

        ResponseEntity<CarrierResponseDTO> response = carrierController.update(carrierRequestDTO, carrierId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(carrierResponseDTO);
        verify(carrierService, times(1)).update(eq(carrierId), any(CarrierRequestDTO.class));
    }

    @Test
    void updateCarrier_notFound_shouldThrowException() {
        int carrierId = 1;
        CarrierRequestDTO carrierRequestDTO = new CarrierRequestDTO();

        when(carrierService.update(eq(carrierId), any(CarrierRequestDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrier not found"));

        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            carrierController.update(carrierRequestDTO, carrierId);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Carrier not found");
    }

    @Test
    void findCarrierById_shouldReturnCarrier() {
        int carrierId = 1;

        CarrierResponseDTO carrierResponseDTO = new CarrierResponseDTO();
        carrierResponseDTO.setId(carrierId);
        carrierResponseDTO.setName("DHL Express");
        carrierResponseDTO.setContactNumber("+49 987 654 321");
        carrierResponseDTO.setEmail("contact@dhl-express.com");
        carrierResponseDTO.setWebsite("https://www.dhl-express.com");

        when(carrierService.findById(carrierId)).thenReturn(carrierResponseDTO);

        ResponseEntity<CarrierResponseDTO> response = carrierController.findById(carrierId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(carrierResponseDTO);
        verify(carrierService, times(1)).findById(carrierId);
    }

    @Test
    void findCarrierById_notFound_shouldThrowException() {
        int carrierId = 1;

        when(carrierService.findById(carrierId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrier not found"));

        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            carrierController.findById(carrierId);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Carrier not found");
    }

    @Test
    void deleteCarrier_shouldReturnNoContent() {
        int carrierId = 1;

        doNothing().when(carrierService).deleteById(carrierId);

        ResponseEntity<Void> response = carrierController.delete(carrierId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(carrierService, times(1)).deleteById(carrierId);
    }

    @Test
    void deleteCarrier_notFound_shouldThrowException() {
        int carrierId = 1;

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrier not found"))
                .when(carrierService).deleteById(carrierId);

        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            carrierController.delete(carrierId);
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Carrier not found");
    }
}
