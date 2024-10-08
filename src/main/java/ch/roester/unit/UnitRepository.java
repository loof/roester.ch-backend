package ch.roester.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends PagingAndSortingRepository<Unit, Integer>, JpaRepository<Unit, Integer>, JpaSpecificationExecutor<Unit> {

}
