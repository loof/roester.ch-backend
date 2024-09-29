package ch.roester.part;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends
        JpaRepository<Part, Integer>,
        PagingAndSortingRepository<Part, Integer>,
        JpaSpecificationExecutor<Part> {
    // You can add custom query methods here if needed in the future
}
