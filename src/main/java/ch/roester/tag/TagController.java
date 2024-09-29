package ch.roester.tag;

import ch.roester.event.EventResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Create a new tag
    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO createdTag = tagService.save(tagRequestDTO);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    // Get a tag by ID
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getTagById(@PathVariable Integer id) {
        TagResponseDTO tagResponseDTO = tagService.findById(id);
        return tagResponseDTO != null ? ResponseEntity.ok(tagResponseDTO) : ResponseEntity.notFound().build();
    }

    // Get all tags
    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(defaultValue = "name") String sortBy) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<TagResponseDTO> tagPages = null;

        // If a search query is provided, use the service to find matching tags
        if (!StringUtils.isEmpty(searchQuery)) {
            tagPages = tagService.findBySearchQuery(searchQuery, paging);
        } else {
            tagPages = tagService.findAll(paging); // Retrieve all tags if no search query is provided
        }

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("tags", tagPages.getContent());
        response.put("currentPage", tagPages.getNumber());
        response.put("totalItems", tagPages.getTotalElements());
        response.put("totalPages", tagPages.getTotalPages());

        return ResponseEntity.ok(response); // Return the response entity
    }

    // Update an existing tag
    @PatchMapping("/{id}")
    public ResponseEntity<TagResponseDTO> updateTag(@PathVariable Integer id, @RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO updatedTag = tagService.update(id, tagRequestDTO);
        return updatedTag != null ? ResponseEntity.ok(updatedTag) : ResponseEntity.notFound().build();
    }

    // Delete a tag by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        try {
            tagService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    // Find tags by name
    @GetMapping("/search")
    public ResponseEntity<Set<TagResponseDTO>> findTagsByName(@RequestParam List<String> tagNames) {
        Set<TagResponseDTO> tags = tagService.findTagsByName(tagNames);
        return ResponseEntity.ok(tags);
    }
}
