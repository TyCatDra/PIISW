package com.example.history.service;

import com.example.history.model.OrderHistory;
import com.example.history.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrderHistoryRepository repository;

    public OrderHistory create(OrderHistory h) {
        return repository.save(h);
    }

    public OrderHistory update(Long id, OrderHistory update) {
        OrderHistory existing = repository.findById(id).orElseThrow();
        existing.setDeliveryStatus(update.getDeliveryStatus());
        return repository.save(existing);
    }

    public List<OrderHistory> getAll() {
        return repository.findAll();
    }

    public OrderHistory getById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
