package ch.roester.shipping_method;


import ch.roester.shipping_method.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingMethodRepository extends PagingAndSortingRepository<ShippingMethod, Integer>, JpaRepository<ShippingMethod, Integer>, JpaSpecificationExecutor<ShippingMethod> {
    List<ShippingMethod> findAllByOrderByWeightInGramsLimitAsc();
}