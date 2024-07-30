package ch.roester.event;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RequestMapping(EventController.REQUEST_MAPPING)
@RestController
@Slf4j
public class EventController {

    public static final String REQUEST_MAPPING = "/events";
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> create(@RequestBody @Valid EventRequestDTO eventRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(eventService.save(eventRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> findById(@Parameter(description = "Id of event to get") @PathVariable("id") Integer id) {
        try {
            EventResponseDTO product = eventService.findById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(defaultValue = "date") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<EventResponseDTO> eventPages = null;

       if (!StringUtils.isEmpty(searchQuery)) {
            eventPages = eventService.findBySearchQuery(searchQuery, paging);
        } else {
            eventPages = eventService.findAll(paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("events", eventPages.getContent());
        response.put("currentPage", eventPages.getNumber());
        response.put("totalItems", eventPages.getTotalElements());
        response.put("totalPages", eventPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/next")
    public ResponseEntity<?> next() {
        return ResponseEntity.ok(eventService.findNext());
    }

    @GetMapping("/last")
    public ResponseEntity<?> last() {
        return ResponseEntity.ok(eventService.findLast());
    }


    @PatchMapping("{id}")
    public ResponseEntity<EventRequestDTO> update(@Valid @RequestBody EventRequestDTO eventRequestDto, @PathVariable("id") Integer id) {
        try {
            EventRequestDTO updatedEvent = eventService.update(id, eventRequestDto);
            return ResponseEntity.ok(updatedEvent);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of event to delete") @PathVariable("id") Integer id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

}