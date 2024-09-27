package ch.roester.unit;

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
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Autowired
    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    public Page<UnitResponseDTO> findAll(Pageable pageable) {
        return unitMapper.toResponseDTO(unitRepository.findAll(pageable));
    }

    public UnitResponseDTO findById(Integer id) {
        return unitMapper.toResponseDTO(unitRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public UnitResponseDTO update(Integer id, UnitRequestDTO updatingUnit) {
        Optional<Unit> existingUnit = unitRepository.findById(id);
        if (existingUnit.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(updatingUnit, existingUnit.get());
        return unitMapper.toResponseDTO(unitRepository.save(existingUnit.get()));
    }

    public UnitResponseDTO save(UnitRequestDTO unit) {
        return unitMapper.toResponseDTO(unitRepository.save(unitMapper.fromRequestDTO(unit)));
    }

    public void deleteById(Integer id) {
        unitRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        unitRepository.deleteById(id);
    }
}
