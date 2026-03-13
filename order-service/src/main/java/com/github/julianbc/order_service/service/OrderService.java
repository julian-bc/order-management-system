package com.github.julianbc.order_service.service;

import com.github.julianbc.order_service.dtos.InventoryResponse;
import com.github.julianbc.order_service.dtos.OrderLineItemsDto;
import com.github.julianbc.order_service.dtos.OrderRequest;
import com.github.julianbc.order_service.model.Order;
import com.github.julianbc.order_service.model.OrderLineItems;
import com.github.julianbc.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);

        List<String> codeSkuList = order.getOrderLineItems().stream()
                        .map(OrderLineItems::getCodeSku)
                                .collect(Collectors.toList());

        InventoryResponse[] inventoryResponseArr = webClient.get()
                        .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder
                                .queryParam("codeSkuList", codeSkuList).build())
                                .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArr)
                        .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            repository.save(order);
        } else {
            throw new IllegalArgumentException("The product isn't in stock");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setCodeSku(orderLineItemsDto.getCodeSku());
        return orderLineItems;
    }
}
