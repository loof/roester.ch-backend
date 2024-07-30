package ch.roester.event;

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
        return eventMapper.toResponseDTO(eventRepository.findNext());
    }

    public EventResponseDTO findLast() {
        return eventMapper.toResponseDTO(eventRepository.findLast());
    }

    public Page<EventResponseDTO> findBySearchQuery(String searchQuery, Pageable pageable) {
        return eventMapper.toResponseDTO(eventRepository.findAllByNameOrDescription(searchQuery, pageable));
    }

    public EventResponseDTO findById(Integer id) {
        return eventMapper.toResponseDTO(eventRepository.findById(id).orElseThrow(EntityNotFoundException::new));
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
}