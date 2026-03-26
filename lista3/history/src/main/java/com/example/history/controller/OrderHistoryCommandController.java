package com.example.history.controller;

import com.example.history.model.OrderHistory;
import com.example.history.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Tag(name = "CREATE / UPDATE")
public class OrderHistoryCommandController {

    private final OrderHistoryService service;

    @PostMapping
    public OrderHistory create(@RequestBody OrderHistory h) {
        return service.create(h);
    }

    @PutMapping("/{id}")
    public OrderHistory update(@PathVariable Long id,
                               @RequestBody OrderHistory h) {
        return service.update(id, h);
    }
}
