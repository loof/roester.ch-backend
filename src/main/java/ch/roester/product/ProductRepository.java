package ch.roester.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {


    @Query("select p from Product p join p.tags t where t.name in :tags")
    Page<Product> findAllByTags(List<String> tags, Pageable pageable);

    @Query("select p from Product p " +
            "where p.name like concat('%', :searchQuery, '%') " +
            "or p.description like concat('%', :searchQuery, '%') ")
    Page<Product> findAllByNameOrDescription(String searchQuery, Pageable pageable);

    @Query("select p from Product p " +
            "join p.tags t " +
            "where t.name in :tags " +
            "and (p.name like concat('%', :searchQuery, '%') " +
            "or p.description like concat('%', :searchQuery, '%'))")
    Page<Product> findAllByNameOrDescriptionAndTags(String searchQuery, List<String> tags, Pageable pageable);


}