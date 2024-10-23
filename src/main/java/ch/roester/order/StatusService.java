package ch.roester.order;

import ch.roester.carrier.CarrierResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    @Autowired
    public StatusService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    public StatusResponseDTO create(StatusRequestDTO statusRequestDTO) {
        Status status = statusMapper.fromRequestDTO(statusRequestDTO);
        Status savedStatus = statusRepository.save(status);
        return statusMapper.toResponseDTO(savedStatus);
    }

    public StatusResponseDTO findById(Integer id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found"));
        return statusMapper.toResponseDTO(status);
    }

    public Page<StatusResponseDTO> findAll(Pageable pageable) {
        return statusRepository.findAll(pageable).map(statusMapper::toResponseDTO);
    }

    public StatusResponseDTO update(Integer id, StatusRequestDTO statusRequestDTO) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status not found"));
        status.setName(statusRequestDTO.getName()); // Update fields as necessary
        Status updatedStatus = statusRepository.save(status);
        return statusMapper.toResponseDTO(updatedStatus);
    }

    public void delete(Integer id) {
        if (!statusRepository.existsById(id)) {
            throw new EntityNotFoundException("Status not found");
        }
        statusRepository.deleteById(id);
    }
}
