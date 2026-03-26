package com.example.history;

import com.example.history.model.OrderHistory;
import com.example.history.repository.OrderHistoryRepository;
import com.example.history.service.OrderHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryApplicationTests {

	@Mock
	private OrderHistoryRepository repo;

	@InjectMocks
	private OrderHistoryService service;

	@Test
	void shouldCreate() {
		OrderHistory h = new OrderHistory();
		when(repo.save(h)).thenReturn(h);

		OrderHistory result = service.create(h);

		assertNotNull(result);
	}

	@Test
	void shouldUpdateStatus() {
		OrderHistory h = new OrderHistory();
		h.setOrderId(1L);

		when(repo.findById(1L)).thenReturn(Optional.of(h));
		when(repo.save(h)).thenReturn(h);

		OrderHistory update = new OrderHistory();
		update.setDeliveryStatus("DELIVERED");

		OrderHistory result = service.update(1L, update);

		assertEquals("DELIVERED", result.getDeliveryStatus());
	}

	@Test
	void shouldGetByID() {
		OrderHistory h = new OrderHistory();
		h.setOrderId(1L);

		when(repo.findById(1L)).thenReturn(Optional.of(h));
		when(repo.save(h)).thenReturn(h);

		service.create(h);

		OrderHistory result = service.getById(1L);

		assertNotNull(result);
	}

	@Test
	void shouldGetAll() {
		OrderHistory h = new OrderHistory();
		h.setOrderId(1L);

		when(repo.findAll()).thenReturn(List.of(h));
		when(repo.save(h)).thenReturn(h);

		service.create(h);

		List<OrderHistory> result = service.getAll();

		assertEquals(1, result.size());
	}
}
