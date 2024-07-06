package ch.roester.property;

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
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
        this.propertyRepository = propertyRepository;
    }

    public Page<PropertyResponseDTO> findAll(Pageable pageable) {
        return propertyMapper.toResponseDTO(propertyRepository.findAll(pageable));
    }

    public Page<PropertyResponseDTO> findBySearchQuery(String searchQuery, Pageable pageable) {
        return propertyMapper.toResponseDTO(propertyRepository.findAllByNameOrDescription(searchQuery, pageable));
    }

    public PropertyResponseDTO findById(Integer id) {
        return propertyMapper.toResponseDTO(propertyRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public PropertyResponseDTO update(Integer id, PropertyRequestDTO updatingProperty) {
        Optional<Property> existingProperty = propertyRepository.findById(id);
        if (existingProperty.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(existingProperty, updatingProperty);
        return propertyMapper.toResponseDTO(propertyRepository.save(existingProperty.get()));
    }

    public PropertyResponseDTO save(PropertyRequestDTO property) {
        return propertyMapper.toResponseDTO(propertyRepository.save(propertyMapper.fromRequestDTO(property)));
    }

    public void deleteById(Integer id) {
        propertyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        propertyRepository.deleteById(id);
    }
}