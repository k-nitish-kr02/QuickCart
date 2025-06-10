package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
}
