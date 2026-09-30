package com.example.demo.service;

import com.example.demo.Dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getMyOrders(String email);

    List<OrderDTO> getAllOrders();

    OrderDTO updateStatus(Long orderId, OrderStatus status);

    Order getEntity(Long id);
}