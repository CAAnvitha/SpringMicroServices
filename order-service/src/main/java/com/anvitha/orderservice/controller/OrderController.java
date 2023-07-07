package com.anvitha.orderservice.controller;

import com.anvitha.orderservice.OrderServiceApplication;
import com.anvitha.orderservice.dto.InventoryResponse;
import com.anvitha.orderservice.dto.OrderRequest;
import com.anvitha.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private  final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @TimeLimiter(name="inventory")
    @CircuitBreaker(name="inventory",fallbackMethod = "handleFallBackMethod")
//    @Retry(name="inventory")
//    public String placeOrder(@RequestBody OrderRequest orderRequest){
//        orderService.placeOrder(orderRequest);
//        return "Order Placed Successfully";
//
//    }

    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));


    }

    public CompletableFuture<String> handleFallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong,please order after");

    }
}
