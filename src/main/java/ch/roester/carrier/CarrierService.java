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
    public CarrierService(CarrierRepository carrierRepository, CarrierMapper carrierMapper) {
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
        return carrierMapper.toResponseDTO(carrierRepository.save(carrier));
    }

    // Method to find all carriers with pagination, sorting, and optional search
    public Page<CarrierResponseDTO> findAll(Pageable pageable) {
        return carrierRepository.findAll(pageable).map(carrierMapper::toResponseDTO);
    }

    // Method to find a carrier by ID
    public CarrierResponseDTO findById(Integer id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrier not found"));
        return carrierMapper.toResponseDTO(carrier);
    }

    // Method to update an existing carrier
    public CarrierResponseDTO update(Integer id, CarrierRequestDTO carrierRequestDTO) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrier not found"));

        carrier.setName(carrierRequestDTO.getName());
        carrier.setContactNumber(carrierRequestDTO.getContactNumber());
        carrier.setEmail(carrierRequestDTO.getEmail());
        carrier.setWebsite(carrierRequestDTO.getWebsite());

        return carrierMapper.toResponseDTO(carrierRepository.save(carrier));
    }

    // Method to delete a carrier by ID
    public void deleteById(Integer id) {
        carrierRepository.deleteById(id);
    }

}
