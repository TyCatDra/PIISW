package com.example.history.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {

    @Id
    private Long orderId;

    private String customerName;
    private String courierName;
    private String deliveryStatus;
    private String productNames;
    private BigDecimal totalPrice;
}
