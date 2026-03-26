package com.example.history.controller;

import com.example.history.model.OrderHistory;
import com.example.history.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Tag(name = "READ")
public class OrderHistoryQueryController {

    private final OrderHistoryService service;

    @GetMapping
    public List<OrderHistory> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public OrderHistory one(@PathVariable Long id) {
        return service.getById(id);
    }
}
