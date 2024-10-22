package ch.roester.product;

import ch.roester.tag.TagMapper;
import ch.roester.tag.TagRepository;
import ch.roester.tag.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final TagMapper tagMapper;
    private final TagService tagService;
    private final TagRepository tagRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, TagRepository tagRepository, ProductMapper productMapper, TagService tagService, TagMapper tagMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.tagRepository = tagRepository;
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


    @Transactional
    public ProductResponseDTO update(Integer id, ProductRequestDTO updatingProductDTO) {
        // Fetch the existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Product updatingProduct = productMapper.fromRequestDTO(updatingProductDTO);

        mergeProducts(existingProduct, updatingProduct);

        // Save the updated product and return the response DTO
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }

    private void mergeProducts(Product existingProduct, Product updatingProduct) {

        // Update only non-null fields from the DTO to the existing Product
        if (updatingProduct.getName() != null) {
            existingProduct.setName(updatingProduct.getName());
        }

        if (updatingProduct.getDescription() != null) {
            existingProduct.setDescription(updatingProduct.getDescription());
        }

        if (updatingProduct.getAmountInStock() != null) {
            existingProduct.setAmountInStock(updatingProduct.getAmountInStock());
        }

        if (updatingProduct.getPricePerUnit() != null) {
            existingProduct.setPricePerUnit(updatingProduct.getPricePerUnit());
        }

        if (updatingProduct.getSoldUnit() != null) {
            existingProduct.setSoldUnit(updatingProduct.getSoldUnit());
        }

        if (updatingProduct.getStock() != null) {
            existingProduct.setStock(updatingProduct.getStock());
        }

        if (updatingProduct.getTags() != null) {
            existingProduct.setTags(updatingProduct.getTags());
        }
    }

    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        return productMapper.toResponseDTO(productRepository.save(productMapper.fromRequestDTO(productRequestDTO)));
    }

    public void deleteById(Integer id) {
        productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productRepository.deleteById(id);
    }
}