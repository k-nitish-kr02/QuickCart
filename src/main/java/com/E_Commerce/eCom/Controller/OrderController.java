package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Payload.OrderPayload.OrderDTO;
import com.E_Commerce.eCom.Payload.OrderPayload.OrderRequestDTO;
import com.E_Commerce.eCom.Service.OrderService;
import com.E_Commerce.eCom.Utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final AuthUtils authUtils;
    private final OrderService orderService;

    public OrderController(AuthUtils authUtils, OrderService orderService) {
        this.authUtils = authUtils;
        this.orderService = orderService;
    }

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO){
        String emailId = authUtils.loggedInEmail();
        System.out.println("orderRequestDTO DATA: " + orderRequestDTO);
        OrderDTO order = orderService.placeOrder(
                emailId,
                orderRequestDTO.getAddressId(),
                paymentMethod,
                orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgPaymentStatus(),
                orderRequestDTO.getPgResponseMessage()
        );
        return ResponseEntity.ok(order);

    }
}
