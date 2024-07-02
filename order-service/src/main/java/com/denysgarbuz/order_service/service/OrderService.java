package com.denysgarbuz.order_service.service;


import com.denysgarbuz.order_service.dto.InventoryResponse;
import com.denysgarbuz.order_service.dto.OrderLineItemsDto;
import com.denysgarbuz.order_service.dto.OrderRequest;
import com.denysgarbuz.order_service.model.Order;
import com.denysgarbuz.order_service.model.OrderLineItems;
import com.denysgarbuz.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor()
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());


        List<OrderLineItems> orderLineItems = orderRequest.orderLineItemsDtos().stream()
                .map(this::mapToOrderLineItems)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        //call inventory service , and place order if product is in stock
        InventoryResponse[] result = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(result)
                .allMatch(InventoryResponse::isInStock);


        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("product is not in stock, please try again later");
        }

    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.price());
        orderLineItems.setQuantity(orderLineItemsDto.quantity());
        orderLineItems.setSkuCode(orderLineItemsDto.skuCode());

        return orderLineItems;
    }
}
