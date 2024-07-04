package ch.roester.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends PagingAndSortingRepository<Position, Integer>, JpaRepository<Position, Integer>, JpaSpecificationExecutor<Position> {

}