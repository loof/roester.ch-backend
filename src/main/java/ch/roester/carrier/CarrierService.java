package ch.roester.carrier;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarrierService {

    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    @Autowired
    public CarrierService(CarrierRepository carrierRepository, @Qualifier("carrierMapper") CarrierMapper carrierMapper) {
        this.carrierRepository = carrierRepository;
        this.carrierMapper = carrierMapper;
    }

    // Method to save a new carrier
    public CarrierResponseDTO save(CarrierRequestDTO carrierRequestDTO) {
        Carrier carrier = new Carrier();
        carrier.setName(carrierRequestDTO.getName());
        carrier.setContactNumber(carrierRequestDTO.getContactNumber());
        carrier.setEmail(carrierRequestDTO.getEmail());
        carrier.setWebsite(carrierRequestDTO.getWebsite());
        // Save the carrier and return the response DTO
        return toResponseDTO(carrierRepository.save(carrier));
    }

    // Method to find all carriers with pagination, sorting, and optional search
    public Page<CarrierResponseDTO> findAll(Pageable pageable) {
        return carrierRepository.findAll(pageable).map(this::toResponseDTO);
    }

    // Method to find a carrier by ID
    public CarrierResponseDTO findById(Integer id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrier not found"));
        return toResponseDTO(carrier);
    }

    // Method to update an existing carrier
    public CarrierResponseDTO update(Integer id, CarrierRequestDTO carrierRequestDTO) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrier not found"));

        carrier.setName(carrierRequestDTO.getName());
        carrier.setContactNumber(carrierRequestDTO.getContactNumber());
        carrier.setEmail(carrierRequestDTO.getEmail());
        carrier.setWebsite(carrierRequestDTO.getWebsite());

        return toResponseDTO(carrierRepository.save(carrier));
    }

    // Method to delete a carrier by ID
    public void deleteById(Integer id) {
        carrierRepository.deleteById(id);
    }

    // Method to convert Carrier to CarrierResponseDTO
    private CarrierResponseDTO toResponseDTO(Carrier carrier) {
        CarrierResponseDTO responseDTO = new CarrierResponseDTO();
        responseDTO.setId(carrier.getId());
        responseDTO.setName(carrier.getName());
        responseDTO.setContactNumber(carrier.getContactNumber());
        responseDTO.setEmail(carrier.getEmail());
        responseDTO.setWebsite(carrier.getWebsite());
        return responseDTO;
    }
}
