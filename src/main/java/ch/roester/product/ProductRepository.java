package ch.roester.product;

import ch.roester.tag.Tag;
import jakarta.validation.constraints.Size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);
    Page<Product> findAllByDescriptionContaining(String description, Pageable pageable);
    Page<Product> findAllByTagsContainingIgnoreCase(Set<Tag> tags, Pageable pageable);


}