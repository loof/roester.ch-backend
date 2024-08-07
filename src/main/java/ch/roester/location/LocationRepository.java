package ch.roester.location;

import ch.roester.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, Integer>, JpaRepository<Location, Integer>, JpaSpecificationExecutor<Location> {
    @Query("select l from Location l " +
            "where l.city like concat('%', :searchQuery, '%') " +
            "or l.street like concat('%', :searchQuery, '%') ")
    Page<Location> findAllByCityOrStreet(String searchQuery, Pageable pageable);
}