package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Payload.CartPayload.CartDTO;
import com.E_Commerce.eCom.Payload.CartPayload.CartResponse;
import com.E_Commerce.eCom.Repository.CartRepo;
import com.E_Commerce.eCom.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService, CartRepo cartRepo) {
        this.cartService = cartService;
    }


    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,@PathVariable Integer quantity){

        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return ResponseEntity.ok(cartDTO);
    }
    @GetMapping("/carts")
    public ResponseEntity<CartResponse> getAllCarts(){
        CartResponse response = cartService.getAllCarts();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCart(){
        CartDTO cartDTO = cartService.getCartWithFullDetails();
        return ResponseEntity.ok(cartDTO);
    }
    @DeleteMapping("/carts/users/cart")
    public ResponseEntity<?> deleteUserCart(){
        cartService.deleteUserCart();
        return new  ResponseEntity<>("deleted", HttpStatus.OK);
    }
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateProductQuantity(@PathVariable Long productId,@PathVariable String operation){
        CartDTO cartDTO = cartService.updateProductQuantity(productId,operation.equalsIgnoreCase("delete")? -1 : +1);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId ,@PathVariable Long productId){
        String status = cartService.deleteProductFromCart(cartId,productId);
        return ResponseEntity.ok(status);
    }

}
