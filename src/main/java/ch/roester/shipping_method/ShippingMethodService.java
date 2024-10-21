package ch.roester.shipping_method;

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
public class ShippingMethodService {

    private final ShippingMethodRepository shippingMethodRepository;
    private final ShippingMethodMapper shippingMethodMapper;

    @Autowired
    public ShippingMethodService(ShippingMethodRepository shippingMethodRepository, ShippingMethodMapper shippingMethodMapper) {
        this.shippingMethodRepository = shippingMethodRepository;
        this.shippingMethodMapper = shippingMethodMapper;
    }

    public Page<ShippingMethodResponseDTO> findAll(Pageable pageable) {
        return shippingMethodMapper.toResponseDTO(shippingMethodRepository.findAll(pageable));
    }

    public ShippingMethodResponseDTO findById(Integer id) {
        return shippingMethodMapper.toResponseDTO(
                shippingMethodRepository.findById(id)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

    public ShippingMethodResponseDTO update(Integer id, ShippingMethodRequestDTO updatingShippingMethod) {
        Optional<ShippingMethod> existingShippingMethod = shippingMethodRepository.findById(id);
        if (existingShippingMethod.isEmpty()) {
            throw new EntityNotFoundException("Shipping Method not found");
        }
        BeanUtils.copyProperties(updatingShippingMethod, existingShippingMethod.get());
        return shippingMethodMapper.toResponseDTO(shippingMethodRepository.save(existingShippingMethod.get()));
    }

    public ShippingMethodResponseDTO save(ShippingMethodRequestDTO shippingMethod) {
        return shippingMethodMapper.toResponseDTO(
                shippingMethodRepository.save(shippingMethodMapper.fromRequestDTO(shippingMethod))
        );
    }

    public void deleteById(Integer id) {
        shippingMethodRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shipping Method not found"));
        shippingMethodRepository.deleteById(id);
    }
}
