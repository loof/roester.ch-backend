package ch.roester.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends PagingAndSortingRepository<Status, Integer>, JpaRepository<Status, Integer>, JpaSpecificationExecutor<Status> {
    Optional<Status> getFirstByName(String name);
}
