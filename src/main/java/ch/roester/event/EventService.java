package ch.roester.event;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    public Page<EventResponseDTO> findAll(Pageable pageable) {
        return eventMapper.toResponseDTO(eventRepository.findAll(pageable));
    }

    public EventResponseDTO findNext() {
        Event event = eventRepository.findNext();
        if (event == null) {
            throw new EntityNotFoundException();
        }
        EventResponseDTO responseDTO = eventMapper.toResponseDTO(event);
        setNextPrev(responseDTO);
        return responseDTO;
    }

    public EventResponseDTO findPrev() {
        Event event = eventRepository.findPrev();
        if (event == null) {
            throw new EntityNotFoundException();
        }
        EventResponseDTO responseDTO = eventMapper.toResponseDTO(event);
        setNextPrev(responseDTO);
        return responseDTO;
    }

    public Page<EventResponseDTO> findBySearchQuery(String searchQuery, Pageable pageable) {
        return eventMapper.toResponseDTO(eventRepository.findAllByNameOrDescription(searchQuery, pageable));
    }

    public EventResponseDTO findById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        EventResponseDTO responseDTO = eventMapper.toResponseDTO(event);
        setNextPrev(responseDTO);
        return eventMapper.toResponseDTO(event);
    }

    public EventResponseDTO findFirstByDate(LocalDateTime date) {
        Event event = eventRepository.findByDateBetween(date.toLocalDate().atStartOfDay(), date.toLocalDate().atStartOfDay().plusDays(1).minusSeconds(1));
        if (event == null) {
            throw new EntityNotFoundException();
        }
        EventResponseDTO eventDTO = eventMapper.toResponseDTO(event);
        setNextPrev(eventDTO);
        return eventDTO;
    }

    public EventResponseDTO update(Integer id, EventRequestDTO updatingEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(existingEvent, updatingEvent);
        return eventMapper.toResponseDTO(eventRepository.save(existingEvent.get()));
    }

    public EventResponseDTO save(EventRequestDTO event) {
        return eventMapper.toResponseDTO(eventRepository.save(eventMapper.fromRequestDTO(event)));
    }

    public void deleteById(Integer id) {
        eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        eventRepository.deleteById(id);
    }

    private void setNextPrev(EventResponseDTO dto) {
        Event nextEvent = eventRepository.findFirstByDateGreaterThanOrderByDateAsc(dto.getDate());
        Event prevEvent = eventRepository.findFirstByDateLessThanOrderByDateDesc(dto.getDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (nextEvent != null) {
            dto.setNextDate(nextEvent.getDate().format(formatter));
        }
        if (prevEvent != null) {
            dto.setPrevDate(prevEvent.getDate().format(formatter));
        }
        Event prev = eventRepository.findPrev();
        Event next = eventRepository.findNext();
        if (prev != null) {
            dto.setIsPrev(prev.getDate() == dto.getDate());
        } else {
            dto.setIsPrev(false);
        }
        if (next != null) {
            dto.setIsNext(next.getDate() == dto.getDate());
        } else {
            dto.setIsNext(false);
        }



    }
}