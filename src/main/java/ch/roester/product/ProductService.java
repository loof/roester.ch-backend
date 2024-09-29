package ch.roester.product;

import ch.roester.tag.TagRepository;
import ch.roester.unit.Unit;
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

@Slf4j
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, TagRepository tagRepository, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productMapper.toResponseDTO(productRepository.findAll(pageable));
    }

    public Page<ProductResponseDTO> findBySearchQuery(String searchQuery, Pageable pageable) {
        return productMapper.toResponseDTO(productRepository.findAllByNameOrDescription(searchQuery, pageable));
    }

    public Page<ProductResponseDTO> findByTags(List<String> tagStrings, Pageable pageable) {
        return productMapper.toResponseDTO(productRepository.findAllByTags(tagStrings, pageable));
    }

    public Page<ProductResponseDTO> findBySearchQueryAndTags(String searchQuery, List<String> tagStrings, Pageable pageable) {
        return productMapper.toResponseDTO(productRepository.findAllByNameOrDescriptionAndTags(searchQuery, tagStrings, pageable));
    }

    public ProductResponseDTO findById(Integer id) {
        return productMapper.toResponseDTO(productRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public ProductResponseDTO update(Integer id, ProductRequestDTO updatingProductDTO) {
        // Fetch the existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Use the ProductMapper to convert the DTO to a Product entity
        Product updatingProductEntity = productMapper.fromRequestDTO(updatingProductDTO);

        // Update only non-null fields from the DTO to the existing Product
        if (updatingProductDTO.getName() != null) {
            existingProduct.setName(updatingProductEntity.getName());
        }

        if (updatingProductDTO.getDescription() != null) {
            existingProduct.setDescription(updatingProductEntity.getDescription());
        }

        if (updatingProductDTO.getAmountInStock() != null) {
            existingProduct.setAmountInStock(updatingProductEntity.getAmountInStock());
        }

        if (updatingProductDTO.getPricePerUnit() != null) {
            existingProduct.setPricePerUnit(updatingProductEntity.getPricePerUnit());
        }

        // The Unit and Stock relationships will be handled by the mapper
        if (updatingProductEntity.getSoldUnit() != null) {
            existingProduct.setSoldUnit(updatingProductEntity.getSoldUnit());
        }

        if (updatingProductEntity.getStock() != null) {
            existingProduct.setStock(updatingProductEntity.getStock());
        }

        // Save the updated product and return the response DTO
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }



    public ProductResponseDTO save(ProductRequestDTO product) {
        return productMapper.toResponseDTO(productRepository.save(productMapper.fromRequestDTO(product)));
    }

    public void deleteById(Integer id) {
        productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productRepository.deleteById(id);
    }
}