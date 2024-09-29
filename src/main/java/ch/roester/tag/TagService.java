package ch.roester.tag;

import ch.roester.event.EventResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    // Find all tags with pagination
    public Page<TagResponseDTO> findAll(Pageable pageable) {
        return tagMapper.toResponseDTO(tagRepository.findAll(pageable));
    }

    public Page<TagResponseDTO> findBySearchQuery(String searchQuery, Pageable pageable) {
        return tagMapper.toResponseDTO(tagRepository.findAllByName(searchQuery, pageable));
    }

    // Find a tag by ID
    public TagResponseDTO findById(Integer id) {
        return tagMapper.toResponseDTO(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    // Update an existing tag
    public TagResponseDTO update(Integer id, TagRequestDTO tagRequestDTO) {
        Optional<Tag> existingTagOpt = tagRepository.findById(id);
        if (existingTagOpt.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Tag existingTag = existingTagOpt.get();
        // Copy properties from the DTO to the existing entity
        BeanUtils.copyProperties(tagRequestDTO, existingTag, "id"); // Exclude id from copying to preserve it
        return tagMapper.toResponseDTO(tagRepository.save(existingTag));
    }

    // Create a new tag
    public TagResponseDTO save(TagRequestDTO tagRequestDTO) {
        Tag tag = tagMapper.fromRequestDTO(tagRequestDTO); // Convert DTO to entity
        return tagMapper.toResponseDTO(tagRepository.save(tag)); // Save and convert back to DTO
    }

    // Delete a tag by ID
    public void deleteById(Integer id) {
        tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        tagRepository.deleteById(id);
    }

    // Find tags by name
    public Set<TagResponseDTO> findTagsByName(List<String> tagNames) {
        Set<Tag> tags = tagRepository.findByNameIsIn(tagNames);
        return tags.stream()
                .map(tagMapper::toResponseDTO)
                .collect(Collectors.toSet());
    }
}
