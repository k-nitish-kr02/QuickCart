package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.productId =?1 AND ci.cart.id = ?2 ")
    CartItem findByProductIdAndCartId(Long productId, Long id);
}
