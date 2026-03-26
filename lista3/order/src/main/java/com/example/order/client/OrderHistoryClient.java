package com.example.order.client;

import com.example.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderHistoryClient {

    private final RestTemplate restTemplate;

    public void createHistory(Order order) {
        restTemplate.postForObject(
                "http://localhost:8082/history",
                map(order),
                Void.class
        );
    }

    public void updateStatus(Order order) {
        restTemplate.put(
                "http://localhost:8082/history/" + order.getId(),
                map(order)
        );
    }

    private Map<String, Object> map(Order order) {
        return Map.of(
                "orderId", order.getId(),
                "customerName", order.getCustomerName(),
                "courierName", order.getDelivery().getCourierName(),
                "deliveryStatus", order.getDelivery().getStatus().name(),
                "productNames", order.getItems().stream()
                        .map(i -> i.getProduct().getName())
                        .collect(Collectors.joining(",")),
                "totalPrice", order.getItems().stream()
                        .map(i -> i.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(i.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}
