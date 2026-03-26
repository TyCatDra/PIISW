package com.example.order.service;

import com.example.order.client.OrderHistoryClient;
import com.example.order.model.DeliveryStatus;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderHistoryClient historyClient;

    public Order create(Order order) {
        order.getDelivery().setStatus(DeliveryStatus.CREATED);
        Order saved = repository.save(order);

        historyClient.createHistory(saved);

        return saved;
    }

    public Order updateStatus(Long id, DeliveryStatus status) {
        Order order = repository.findById(id).orElseThrow();
        order.getDelivery().setStatus(status);

        Order updated = repository.save(order);

        historyClient.updateStatus(updated);

        return updated;
    }
}
