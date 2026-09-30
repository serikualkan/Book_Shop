package com.example.demo.Dto;


import com.example.demo.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private List<OrderItemDTO> items;
    private Double totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
