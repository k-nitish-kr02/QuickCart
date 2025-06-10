package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {
}
