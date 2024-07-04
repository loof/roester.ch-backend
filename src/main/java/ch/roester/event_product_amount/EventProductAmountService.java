package ch.roester.event_product_amount;

import ch.roester.event.*;
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
public class EventProductAmountService {
    private final EventProductAmountRepository eventProductAmountRepository;
    private final EventProductAmountMapper eventProductAmountMapper;

    @Autowired
    public EventProductAmountService(EventProductAmountRepository eventProductAmountRepository, EventProductAmountMapper eventProductAmountMapper) {
        this.eventProductAmountMapper = eventProductAmountMapper;
        this.eventProductAmountRepository = eventProductAmountRepository;
    }

    public Page<EventProductAmountResponseDTO> findAll(Pageable pageable) {
        return eventProductAmountMapper.toResponseDTO(eventProductAmountRepository.findAll(pageable));
    }


    public EventProductAmountResponseDTO findById(Integer id) {
        return eventProductAmountMapper.toResponseDTO(eventProductAmountRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public EventProductAmountResponseDTO update(Integer id, EventProductAmountRequestDTO updatingEventProductAmount) {
        Optional<EventProductAmount> existingEventProductAmount = eventProductAmountRepository.findById(id);
        if (existingEventProductAmount.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(existingEventProductAmount, updatingEventProductAmount);
        return eventProductAmountMapper.toResponseDTO(eventProductAmountRepository.save(existingEventProductAmount.get()));
    }

    public EventProductAmountResponseDTO save(EventProductAmountRequestDTO eventProductAmount) {
        return eventProductAmountMapper.toResponseDTO(eventProductAmountRepository.save(eventProductAmountMapper.fromRequestDTO(eventProductAmount)));
    }

    public void deleteById(Integer id) {
        eventProductAmountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        eventProductAmountRepository.deleteById(id);
    }
}