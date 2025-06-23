package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.productId =?1 AND ci.cart.cartId = ?2 ")
    CartItem findByProductIdAndCartId(Long productId, Long id);

    @Query("select ci from CartItem  ci where ci.product.productId = ?1")
    List<CartItem> findByProductId(Long productId);
}
