package ch.roester.location;

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


@RequestMapping(LocationController.REQUEST_MAPPING)
@RestController
@Slf4j
public class LocationController {

    public static final String REQUEST_MAPPING = "/locations";
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationResponseDTO> create(@RequestBody @Valid LocationRequestDTO locationRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(locationService.save(locationRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> findById(@Parameter(description = "Id of location to get") @PathVariable("id") Integer id) {
        try {
            LocationResponseDTO location = locationService.findById(id);
            return ResponseEntity.ok(location);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(defaultValue = "date") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<LocationResponseDTO> locationPages = null;

       if (!StringUtils.isEmpty(searchQuery)) {
            locationPages = locationService.findBySearchQuery(searchQuery, paging);
        } else {
            locationPages = locationService.findAll(paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("locations", locationPages.getContent());
        response.put("currentPage", locationPages.getNumber());
        response.put("totalItems", locationPages.getTotalElements());
        response.put("totalPages", locationPages.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @PatchMapping("{id}")
    public ResponseEntity<LocationRequestDTO> update(@Valid @RequestBody LocationRequestDTO locationRequestDTO, @PathVariable("id") Integer id) {
        try {
            LocationRequestDTO updatedLocation = locationService.update(id, locationRequestDTO);
            return ResponseEntity.ok(updatedLocation);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of location to delete") @PathVariable("id") Integer id) {
        try {
            locationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
    }

}