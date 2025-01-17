package com.denysgarbuz.order_service.controller;


import com.denysgarbuz.order_service.dto.OrderRequest;
import com.denysgarbuz.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest order) {
        this.orderService.placeOrder(order);
        return "Order placed successfully";
    }


}
