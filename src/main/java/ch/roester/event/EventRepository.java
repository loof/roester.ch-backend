package ch.roester.event;

import ch.roester.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer>, JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {


    @Query("select e from Event e " +
            "where e.name like concat('%', :searchQuery, '%') " +
            "or e.description like concat('%', :searchQuery, '%') ")
    Page<Event> findAllByNameOrDescription(String searchQuery, Pageable pageable);


}