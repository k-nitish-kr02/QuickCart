package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.ExceptionHandler.ResourceNotFoundException;
import com.E_Commerce.eCom.Model.Cart;
import com.E_Commerce.eCom.Model.CartItem;
import com.E_Commerce.eCom.Model.Product;
import com.E_Commerce.eCom.Payload.CartPayload.CartDTO;
import com.E_Commerce.eCom.Payload.CartPayload.CartResponse;
import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import com.E_Commerce.eCom.Repository.CartItemRepo;
import com.E_Commerce.eCom.Repository.CartRepo;
import com.E_Commerce.eCom.Repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.E_Commerce.eCom.Utils.AuthUtils;

import java.util.*;

@Service
public class CartService {

    private final CartItemRepo cartItemRepo;
    private final ModelMapper modelMapper;
    private final ProductRepo productRepo;
    private final CartRepo cartRepo;
    private final AuthUtils authUtil;


    @Autowired
    public CartService(CartItemRepo cartItemRepo, ModelMapper modelMapper, AuthUtils authUtil, CartRepo cartRepo, ProductRepo productRepo) {
        this.cartItemRepo = cartItemRepo;
        this.modelMapper = modelMapper;
        this.authUtil = authUtil;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    private Cart createCart(){
        Cart usrCart = cartRepo.findByUsername(authUtil.loggedInUsername());
        if(usrCart!=null) {
            return usrCart;
        }

        usrCart = new Cart(0.0,authUtil.getUser());

        return cartRepo.save(usrCart);

    }

    private CartDTO createCartDTO(Cart cart){

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(cartItem ->
                {
                    ProductDTO productDTO = modelMapper.map(cartItem.getProduct(), ProductDTO.class);
                    productDTO.setQuantity(cartItem.getQuantity());
                    return productDTO;
                })
                .toList();
        cartDTO.setProducts(productDTOS);
        return cartDTO;

    }
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // check if product is present
        Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        // check if orderQuantity < stockQuantity
        if( product.getQuantity() ==0) {
            throw new APIException("Out of Stock , plz check later .. ");
        }
        if(product.getQuantity() < quantity){
            throw new APIException("Please reduce the quantity .. ");
        }
        //check if cart is present or not , if not create new one
        Cart usrCart = createCart();

        //check for CartItem if not present , generate CartITem
        CartItem usrCartItem = cartItemRepo.findByProductIdAndCartId(productId,usrCart.getCartId());

        if(usrCartItem == null ){
            usrCartItem = new CartItem();
            usrCartItem.setCart(usrCart);
            usrCartItem.setProduct(product);
            usrCartItem.setItemPrice(product.getPrice());
            usrCartItem.setItemDiscount(product.getDiscount());
        }
        usrCartItem.setQuantity(quantity);

        cartItemRepo.save(usrCartItem);
        //add the cartItem to cart
        List<CartItem> usrCartItems =  usrCart.getCartItems();

        usrCartItems.add(usrCartItem);
        usrCart.setCartItems(usrCartItems);


        usrCart.setTotalPrice(usrCart.getTotalPrice()+ product.getSpecialPrice() * quantity);

        // return cartDTO
        return createCartDTO(cartRepo.save(usrCart));
    }

    public CartResponse getAllCarts() {
        List<Cart> carts = Optional.of(cartRepo.findAll()).orElseThrow(()-> new APIException("Either No carts created or all are deleted.."));

        List<CartDTO> cartDTOs = new ArrayList<>();

        for (Cart cart : carts) {
            CartDTO cartDTO = createCartDTO(cart);
            cartDTOs.add(cartDTO);
        }

        return new CartResponse(cartDTOs);

    }

    public CartDTO getCartWithFullDetails(){
        Cart cart = cartRepo.findByUsername(authUtil.loggedInUsername());
        if(cart == null ) throw new APIException("No Cart Found.");

        return createCartDTO(cart);
    }

    @Transactional
    //Method is not working ??
    public void deleteUserCart() {
        Cart cart = cartRepo.findByUsername(authUtil.loggedInUsername());
        if(cart == null ) throw new APIException("No Cart Found.");

        cart.setUserNull(cart);
        cartRepo.delete(cart);

    }

    public CartDTO updateProductQuantity(Long productId, Integer quantity) {
        Cart cart = Optional.ofNullable(cartRepo.findByUsername(authUtil.loggedInUsername())).orElseThrow(()-> new ResourceNotFoundException("Cart","cart.username", authUtil.loggedInUsername()));

        Product product = productRepo.findById(productId).orElseThrow(()-> new APIException("No product found having productId : ${productId}"));

        // check if orderQuantity < stockQuantity
        if( product.getQuantity() == 0) {
            throw new APIException("Out of Stock , plz check later .. ");
        }
        if(product.getQuantity() < quantity){
            throw new APIException("Please reduce the quantity .. ");
        }

        CartItem cartItem = Optional.ofNullable(cartItemRepo.findByProductIdAndCartId(productId, cart.getCartId())).orElseThrow(()-> new APIException("Product is not added to Cart.."));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        //updating total price of cart
        cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getItemPrice() - (cartItem.getItemPrice() * cartItem.getItemDiscount() * 0.01) )* quantity);

        Cart savedCart = cartRepo.save(cart);
        CartItem savedCartItem = cartItemRepo.save(cartItem);
        if(savedCartItem.getQuantity() == 0) cartItemRepo.deleteById(savedCartItem.getCartItemId());
        return createCartDTO(savedCart);

    }

    @Transactional
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","cartId",cartId));

        CartItem cartItem = Optional.ofNullable(cartItemRepo.findByProductIdAndCartId(productId, cartId)).orElseThrow(()-> new APIException("Product is not added to Cart.."));

//        Product product = cartItem.getProduct();

        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getItemPrice() *(1 - cartItem.getItemDiscount() * 0.01) * cartItem.getQuantity());

//        product.getCartItems().remove(cartItem);
//        cart.getCartItems().remove(cartItem);
        cart.removeCartItem(cartItem);


        return "Product " + cartItem.getProduct().getProductName() + " removed from cart.";

    }
}
