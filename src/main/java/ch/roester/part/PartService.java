package ch.roester.part;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {

    private final PartRepository partRepository;
    private final PartMapper partMapper;

    @Autowired
    public PartService(PartRepository partRepository, PartMapper partMapper) {
        this.partRepository = partRepository;
        this.partMapper = partMapper;
    }

    public List<PartResponseDTO> findAll() {
        return partMapper.toResponseDTO(partRepository.findAll());
    }

    public PartResponseDTO findById(Integer id) {
        return partMapper.toResponseDTO(partRepository.findById(id).orElseThrow(EntityNotFoundException::new)); // Throw exception if not found
    }

    public PartResponseDTO create(PartRequestDTO partRequestDTO) {
        Part part = partMapper.fromRequestDTO(partRequestDTO);
        Part savedPart = partRepository.save(part);
        return partMapper.toResponseDTO(savedPart);
    }

    public PartResponseDTO update(Integer id, PartRequestDTO partRequestDTO) {
        Part part = partMapper.fromRequestDTO(partRequestDTO);
        part.setId(id);
        Part updatedPart = partRepository.save(part);
        return partMapper.toResponseDTO(updatedPart);
    }

    public void delete(Integer id) {
        partRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        partRepository.deleteById(id);
    }
}
