package ch.roester.property;

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


@RequestMapping(PropertyController.REQUEST_MAPPING)
@RestController
@Slf4j
public class PropertyController {

    public static final String REQUEST_MAPPING = "/properties";
    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<PropertyResponseDTO> create(@RequestBody @Valid PropertyRequestDTO propertyRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.save(propertyRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> findById(@Parameter(description = "Id of property to get") @PathVariable("id") Integer id) {
        try {
            PropertyResponseDTO property = propertyService.findById(id);
            return ResponseEntity.ok(property);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(defaultValue = "date") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<PropertyResponseDTO> propertyPages = null;

       if (!StringUtils.isEmpty(searchQuery)) {
            propertyPages = propertyService.findBySearchQuery(searchQuery, paging);
        } else {
            propertyPages = propertyService.findAll(paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("properties", propertyPages.getContent());
        response.put("currentPage", propertyPages.getNumber());
        response.put("totalItems", propertyPages.getTotalElements());
        response.put("totalPages", propertyPages.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @PatchMapping("{id}")
    public ResponseEntity<PropertyRequestDTO> update(@Valid @RequestBody PropertyRequestDTO propertyRequestDTO, @PathVariable("id") Integer id) {
        try {
            PropertyRequestDTO updatedProperty = propertyService.update(id, propertyRequestDTO);
            return ResponseEntity.ok(updatedProperty);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Property could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of Property to delete") @PathVariable("id") Integer id) {
        try {
            propertyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }
    }

}