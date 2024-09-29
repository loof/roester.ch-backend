package ch.roester.part;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/parts")
@Slf4j
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @PostMapping
    public ResponseEntity<PartResponseDTO> create(@RequestBody @Valid PartRequestDTO partRequestDTO) {
        try {
            PartResponseDTO createdPart = partService.create(partRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPart);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Part could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartResponseDTO> findById(@Parameter(description = "Id of part to get") @PathVariable("id") Integer id) {
        PartResponseDTO part = partService.findById(id);
        return ResponseEntity.ok(part);
    }

    @GetMapping
    public ResponseEntity<List<PartResponseDTO>> findAll() {
        List<PartResponseDTO> parts = partService.findAll();
        return ResponseEntity.ok(parts);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartResponseDTO> update(@RequestBody @Valid PartRequestDTO partRequestDTO, @PathVariable("id") Integer id) {
        try {
            PartResponseDTO updatedPart = partService.update(id, partRequestDTO);
            return ResponseEntity.ok(updatedPart);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Part could not be updated");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of part to delete") @PathVariable("id") Integer id) {
        try {
            partService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found");
        }
    }
}
