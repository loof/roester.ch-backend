package ch.roester.product;

import ch.roester.tag.TagRepository;
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
        Product existingProduct = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        updatingProductDTO.setId(id);

        if (existingProduct == null) {
            throw new EntityNotFoundException();
        }

        Product updatingProductEntity = productMapper.fromRequestDTO(updatingProductDTO);

        if (updatingProductDTO.getName() == null) {
            updatingProductDTO.setName(existingProduct.getName());
        }

        if (updatingProductDTO.getDescription() == null) {
            updatingProductDTO.setDescription(existingProduct.getDescription());
        }

        BeanUtils.copyProperties(productMapper.fromRequestDTO(updatingProductDTO), existingProduct);
        return productMapper.toResponseDTO(productRepository.save(existingProduct));
    }

    public ProductResponseDTO save(ProductRequestDTO product) {
        return productMapper.toResponseDTO(productRepository.save(productMapper.fromRequestDTO(product)));
    }

    public void deleteById(Integer id) {
        productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productRepository.deleteById(id);
    }
}