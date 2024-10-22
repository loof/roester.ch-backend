package ch.roester.carrier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRepository extends PagingAndSortingRepository<Carrier, Integer>, JpaRepository<Carrier, Integer>, JpaSpecificationExecutor<Carrier> {
    // You can define custom queries here if needed, but basic CRUD operations are provided by JpaRepository
}
