package ch.roester.event_product_amount;

import ch.roester.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventProductAmountRepository extends PagingAndSortingRepository<EventProductAmount, Integer>, JpaRepository<EventProductAmount, Integer>, JpaSpecificationExecutor<EventProductAmount> {

}