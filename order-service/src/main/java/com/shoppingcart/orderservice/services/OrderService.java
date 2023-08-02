package com.shoppingcart.orderservice.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.shoppingcart.orderservice.dto.InventoryResponse;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.shoppingcart.orderservice.dto.OrderLineItemsDto;
import com.shoppingcart.orderservice.dto.OrderRequest;
import com.shoppingcart.orderservice.model.Order;
import com.shoppingcart.orderservice.model.OrderLineItems;
import com.shoppingcart.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    public String saveOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(orderLineItem -> maptoDto(orderLineItem)).toList();
        order.setOrderLineItemsList(orderLineItems);

        // collect all skucodes
        List<String> skuCodes  = order.getOrderLineItemsList().stream().map(orderLineItems1 -> orderLineItems1.getSkucode() )
                .toList();


        Span inventoyServicelookup = tracer.nextSpan().name("InventoryServiceLookUp");
        try(Tracer.SpanInScope spanInScope= tracer.withSpan(inventoyServicelookup.start())){

            // call inventory api to verify stock is present
            InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build() )
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.isInStock() );

            if(allProductsInStock){
                orderRepository.save(order);
                return  "order placed sucessfully";
            } else {
                throw new IllegalArgumentException("Product is out of stock please try again later");
            }

        }finally {
            inventoyServicelookup.end();
        }
    }

    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItem) {
        return OrderLineItems.builder()
            .skucode(orderLineItem.getSkuCode())
            .price(orderLineItem.getPrice())
            .quantity(orderLineItem.getQuantity())
            .build();
    }

}
