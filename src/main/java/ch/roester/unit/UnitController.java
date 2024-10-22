package ch.roester.unit;

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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(UnitController.REQUEST_MAPPING)
@RestController
@Slf4j
public class UnitController {

    public static final String REQUEST_MAPPING = "/units";
    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<UnitResponseDTO> create(@RequestBody @Valid UnitRequestDTO unitRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(unitService.save(unitRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> findById(@Parameter(description = "Id of unit to get") @PathVariable("id") Integer id) {
        try {
            UnitResponseDTO unit = unitService.findById(id);
            return ResponseEntity.ok(unit);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(defaultValue = "date") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<UnitResponseDTO> unitPages;
        unitPages = unitService.findAll(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("units", unitPages.getContent());
        response.put("currentPage", unitPages.getNumber());
        response.put("totalItems", unitPages.getTotalElements());
        response.put("totalPages", unitPages.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UnitRequestDTO> update(@Valid @RequestBody UnitRequestDTO unitRequestDTO, @PathVariable("id") Integer id) {
        try {
            UnitRequestDTO updatedUnit = unitService.update(id, unitRequestDTO);
            return ResponseEntity.ok(updatedUnit);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Unit could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of unit to delete") @PathVariable("id") Integer id) {
        try {
            unitService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found");
        }
    }
}
