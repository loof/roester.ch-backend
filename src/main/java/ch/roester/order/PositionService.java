package ch.roester.order;

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
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Autowired
    public PositionService(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionMapper = positionMapper;
        this.positionRepository = positionRepository;
    }

    public Page<PositionResponseDTO> findAll(Pageable pageable) {
        return positionMapper.toResponseDTO(positionRepository.findAll(pageable));
    }

    public PositionResponseDTO findById(Integer id) {
        return positionMapper.toResponseDTO(positionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public PositionResponseDTO update(Integer id, PositionRequestDTO updatingPosition) {
        Optional<Position> existingPosition = positionRepository.findById(id);
        if (existingPosition.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(updatingPosition, existingPosition);
        return positionMapper.toResponseDTO(positionRepository.save(existingPosition.get()));
    }

    public PositionResponseDTO save(PositionRequestDTO position) {
        return positionMapper.toResponseDTO(positionRepository.save(positionMapper.fromRequestDTO(position)));
    }

    public void deleteById(Integer id) {
        positionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        positionRepository.deleteById(id);
    }
}