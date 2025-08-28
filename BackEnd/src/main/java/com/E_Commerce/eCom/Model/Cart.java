package com.E_Commerce.eCom.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Double totalPrice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    // lazy is generating ConcurrentModificationException ?? main issue @Data which is causing hashcode and equal code function error for Jpa to load lazy  fetch type ,need to study
    private List<CartItem> cartItems = new ArrayList<>();

    public  Cart(double totalPrice, User user) {
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
    }
    public void setUserNull(Cart cart) {
        cart.setUser(null);
    }


}
