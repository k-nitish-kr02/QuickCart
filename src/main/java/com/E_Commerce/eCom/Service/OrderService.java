package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.ExceptionHandler.ResourceNotFoundException;
import com.E_Commerce.eCom.Model.*;
import com.E_Commerce.eCom.Payload.OrderPayload.OrderDTO;
import com.E_Commerce.eCom.Payload.OrderPayload.OrderItemDTO;
import com.E_Commerce.eCom.Repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartRepo cartRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;
    private final AddressRepo addressRepo;
    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(CartRepo cartRepo, OrderItemRepo orderItemRepo, ProductRepo productRepo, CartService cartService, AddressRepo addressRepo, PaymentRepo paymentRepo, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.cartRepo = cartRepo;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
        this.cartService = cartService;
        this.addressRepo = addressRepo;
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgPaymentStatus, String pgResponseMessage) {

        Cart cart = cartRepo.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "email", emailId);
        }

        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted !");
        order.setAddress(address);

        Payment payment = new Payment(pgPaymentId, pgPaymentStatus, pgResponseMessage, pgName,paymentMethod);
        payment.setOrder(order);
        payment = paymentRepo.save(payment);
        order.setPayment(payment);

        Order savedOrder = orderRepo.save(order);

        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems()); //making copy of entity to make it detach so that to avoid concurrentModificationException
        if (cartItems.isEmpty()) {
            throw new APIException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            if (cartItem.getQuantity() <= cartItem.getProduct().getQuantity()) {
                orderItem.setQuantity(cartItem.getQuantity());
            }else{
                throw new APIException("please reduce the quantity of product " + cartItem.getProduct().getProductName() + " ,not enough stock.");
            }
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setDiscount(cartItem.getItemDiscount());
            orderItem.setOrderedProductPrice(cartItem.getItemPrice());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }

        orderItems = orderItemRepo.saveAll(orderItems);

        cartItems.forEach(item -> {
            int quantity = item.getQuantity();
            Product product = item.getProduct();

            // Reduce stock quantity
            product.setQuantity(product.getQuantity() - quantity);

            // Save product back to the database
            productRepo.save(product);

            // Remove items from cart
            cartService.deleteProductFromCart(cart.getCartId(), item.getProduct().getProductId());
        });

        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));

        orderDTO.setAddressId(addressId);

        return orderDTO;
    }
}
