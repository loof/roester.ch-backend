package ch.roester.variant;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class VariantService {
    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;

    @Autowired
    public VariantService(VariantRepository variantRepository, VariantMapper variantMapper) {
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
    }

    public Page<VariantResponseDTO> findAll(Pageable pageable) {
        return variantMapper.toResponseDTO(variantRepository.findAll(pageable));
    }

    public VariantResponseDTO findById(Integer id) {
        return variantMapper.toResponseDTO(variantRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public VariantResponseDTO update(Integer id, VariantRequestDTO updatingVariant) {
        Optional<Variant> existingVariant = variantRepository.findById(id);
        if (existingVariant.isEmpty()) {
            throw new EntityNotFoundException();
        }
        // Copy properties from the DTO to the existing entity
        BeanUtils.copyProperties(updatingVariant, existingVariant.get());
        return variantMapper.toResponseDTO(variantRepository.save(existingVariant.get()));
    }

    public VariantResponseDTO save(VariantRequestDTO variantRequestDTO) {
        return variantMapper.toResponseDTO(variantRepository.save(variantMapper.fromRequestDTO(variantRequestDTO)));
    }

    public void deleteById(Integer id) {
        variantRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        variantRepository.deleteById(id);
    }
}
