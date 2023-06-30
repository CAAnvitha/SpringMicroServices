package com.anvitha.orderservice.controller;

import com.anvitha.orderservice.OrderServiceApplication;
import com.anvitha.orderservice.dto.InventoryResponse;
import com.anvitha.orderservice.dto.OrderRequest;
import com.anvitha.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private  final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory",fallbackMethod = "handleFallBackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";

    }

    public String handleFallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return  "Oops! ";
    }
}
