package com.anvitha.orderservice.service;

import com.anvitha.orderservice.dto.InventoryResponse;
import com.anvitha.orderservice.dto.OrderLineItemsDto;
import com.anvitha.orderservice.dto.OrderRequest;
import com.anvitha.orderservice.event.OrderPlacedEvent;
import com.anvitha.orderservice.model.Order;
import com.anvitha.orderservice.model.OrderLineItems;
import com.anvitha.orderservice.reposirtory.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private  final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private  final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;


    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        log.info("Calling Inventory Service");



      InventoryResponse[]  inventoryResponseArray = webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class)
                        .block();


        boolean allProductsInStock
                = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
        if(allProductsInStock){

            orderRepository.save(order);
//            kafkaTemplate.send("notificationTopic",order.getOrderNumber());
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));

            return  "Order Placed Successfully";
        }else{
            throw new IllegalArgumentException("Product is not in stock , please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
       OrderLineItems orderLineItems  = new OrderLineItems();
       orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
       orderLineItems.setPrice(orderLineItemsDto.getPrice());
       orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
       return  orderLineItems;
    }
}
