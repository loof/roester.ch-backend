package ch.roester.shipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends PagingAndSortingRepository<Shipment, Integer>, JpaRepository<Shipment, Integer>, JpaSpecificationExecutor<Shipment> {
    // You can define custom queries here if needed, but basic CRUD operations are provided by JpaRepository
}
