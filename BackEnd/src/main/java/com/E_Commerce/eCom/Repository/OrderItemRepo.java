package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {

    @Query("select oi from OrderItem oi join fetch oi.product where oi.product.productId = ?1")
    List<OrderItem> findByProductId(Long productId);
}
