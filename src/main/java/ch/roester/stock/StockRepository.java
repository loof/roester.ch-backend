package ch.roester.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends
        PagingAndSortingRepository<Stock, Integer>,
        JpaRepository<Stock, Integer>,
        JpaSpecificationExecutor<Stock> {
    // You can add custom query methods here if needed in the future
}
