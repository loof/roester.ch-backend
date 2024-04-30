package ch.roester.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product save(Product coffee) {
        return repository.save(coffee);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public Product findById(Integer id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Product> findByCondition(ProductRequestDTO coffeeDto, Pageable pageable) {
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    public Product update(Product updatingCoffee, Integer id) {
        Product existingCoffee = findById(id);
        BeanUtils.copyProperties(existingCoffee, updatingCoffee);
        return save(existingCoffee);
    }
}