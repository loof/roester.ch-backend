package ch.roester.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends PagingAndSortingRepository<CartItem, Integer>, JpaRepository<CartItem, Integer>, JpaSpecificationExecutor<CartItem> {

}