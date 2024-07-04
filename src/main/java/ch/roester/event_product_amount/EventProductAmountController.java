package ch.roester.event_product_amount;

import ch.roester.event.EventRequestDTO;
import ch.roester.event.EventResponseDTO;
import ch.roester.event.EventService;
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


@RequestMapping(EventProductAmountController.REQUEST_MAPPING)
@RestController
@Slf4j
public class EventProductAmountController {

    public static final String REQUEST_MAPPING = "/event-product-amounts";
    private final EventProductAmountService eventProductAmountService;

    @Autowired
    public EventProductAmountController(EventProductAmountService eventProductAmountService) {
        this.eventProductAmountService = eventProductAmountService;
    }

    @PostMapping
    public ResponseEntity<EventProductAmountResponseDTO> create(@RequestBody @Valid EventProductAmountRequestDTO eventProductAmountRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(eventProductAmountService.save(eventProductAmountRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event product amount could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventProductAmountResponseDTO> findById(@Parameter(description = "Id of event product amount to get") @PathVariable("id") Integer id) {
        try {
            EventProductAmountResponseDTO eventProductAmount = eventProductAmountService.findById(id);
            return ResponseEntity.ok(eventProductAmount);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event product amount not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<EventProductAmountResponseDTO> eventProductAmountPages = null;
        eventProductAmountPages = eventProductAmountService.findAll(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("eventProductAmounts", eventProductAmountPages.getContent());
        response.put("currentPage", eventProductAmountPages.getNumber());
        response.put("totalItems", eventProductAmountPages.getTotalElements());
        response.put("totalPages", eventProductAmountPages.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @PatchMapping("{id}")
    public ResponseEntity<EventProductAmountRequestDTO> update(@Valid @RequestBody EventProductAmountRequestDTO eventProductAmountRequestDto, @PathVariable("id") Integer id) {
        try {
            EventProductAmountRequestDTO updatedEventProductAmount = eventProductAmountService.update(id, eventProductAmountRequestDto);
            return ResponseEntity.ok(updatedEventProductAmount);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event product amount could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event product amount not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of event product amount to delete") @PathVariable("id") Integer id) {
        try {
            eventProductAmountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event product amount not found");
        }
    }

}