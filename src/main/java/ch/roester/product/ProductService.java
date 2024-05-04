package ch.roester.product;

import ch.roester.tag.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository repository, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.repository = repository;
    }

    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productMapper.toResponseDTO(repository.findAll(pageable));
    }

    public Page<Product> findAllByNameContaining(String name, Pageable pageable) {
        return repository.findAllByNameContaining(name, pageable);
    }

    public Page<Product> findAllByDescriptionContaining(String description, Pageable pageable) {
        return repository.findAllByDescriptionContaining(description, pageable);
    }

    public Page<Product> findAllByTagNamesContainingIgnoreCase(String tagNamesString, Pageable pageable) {
        String[] tagNames = tagNamesString.split(",");
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
        }
        return repository.findAllByTagsContainingIgnoreCase(tags, pageable);
    }



    public Product save(Product product) {
        return repository.save(product);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Product findById(Integer id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Product> findByCondition(ProductRequestDTO requestDTO, Pageable pageable) {
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    public Product update(Integer id, Product updatingProduct) {
        Product existingProduct = findById(id);
        BeanUtils.copyProperties(existingProduct, updatingProduct);
        return save(existingProduct);
    }
}