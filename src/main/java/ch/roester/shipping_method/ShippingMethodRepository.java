package ch.roester.shipping_method;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingMethodRepository extends PagingAndSortingRepository<ShippingMethod, Integer>, JpaRepository<ShippingMethod, Integer>, JpaSpecificationExecutor<ShippingMethod> {

}