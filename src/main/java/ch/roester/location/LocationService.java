package ch.roester.location;

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
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper ) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
    }

    public Page<LocationResponseDTO> findAll(Pageable pageable) {
        return locationMapper.toResponseDTO(locationRepository.findAll(pageable));
    }

    public LocationResponseDTO findById(Integer id) {
        return locationMapper.toResponseDTO(locationRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public Page<LocationResponseDTO> findBySearchQuery(String searchQuery, Pageable pageable) {
        return locationMapper.toResponseDTO(locationRepository.findAllByCityOrStreet(searchQuery, pageable));
    }

    public LocationResponseDTO update(Integer id, LocationRequestDTO updatingLocation) {
        Optional<Location> existingLocation = locationRepository.findById(id);
        if (existingLocation.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(updatingLocation, existingLocation);
        return locationMapper.toResponseDTO(locationRepository.save(existingLocation.get()));
    }

    public LocationResponseDTO save(LocationRequestDTO location) {
        return locationMapper.toResponseDTO(locationRepository.save(locationMapper.fromRequestDTO(location)));
    }

    public void deleteById(Integer id) {
        locationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        locationRepository.deleteById(id);
    }
}