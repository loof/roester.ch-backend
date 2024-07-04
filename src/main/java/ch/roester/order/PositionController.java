package ch.roester.order;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RequestMapping(PositionController.REQUEST_MAPPING)
@RestController
@Slf4j
public class PositionController {

    public static final String REQUEST_MAPPING = "/positions";
    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping
    public ResponseEntity<PositionResponseDTO> create(@RequestBody @Valid PositionRequestDTO positionRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(positionService.save(positionRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Position could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> findById(@Parameter(description = "Id of position to get") @PathVariable("id") Integer id) {
        try {
            PositionResponseDTO position = positionService.findById(id);
            return ResponseEntity.ok(position);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<PositionResponseDTO> positionPages = null;
        positionPages = positionService.findAll(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("positions", positionPages.getContent());
        response.put("currentPage", positionPages.getNumber());
        response.put("totalItems", positionPages.getTotalElements());
        response.put("totalPages", positionPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<PositionRequestDTO> update(@Valid @RequestBody PositionRequestDTO positionRequestDTO, @PathVariable("id") Integer id) {
        try {
            PositionRequestDTO updatedPosition = positionService.update(id, positionRequestDTO);
            return ResponseEntity.ok(updatedPosition);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Position could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of position to delete") @PathVariable("id") Integer id) {
        try {
            positionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position not found");
        }
    }

}