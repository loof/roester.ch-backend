package ch.roester.property;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends PagingAndSortingRepository<Property, Integer>, JpaRepository<Property, Integer>, JpaSpecificationExecutor<Property> {


    @Query("select p from Property p " +
            "where p.name like concat('%', :searchQuery, '%') " +
            "or p.description like concat('%', :searchQuery, '%') ")
    Page<Property> findAllByNameOrDescription(String searchQuery, Pageable pageable);


}