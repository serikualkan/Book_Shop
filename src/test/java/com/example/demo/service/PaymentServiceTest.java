package com.example.demo.service;

import com.example.demo.Dto.PaymentDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void pay() {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setTotalPrice(9000.0);
        orderRepository.save(order);

        PaymentDTO paymentDTO = paymentService.pay(order.getId());

        Order updatedOrder =
                orderRepository.findById(order.getId()).orElseThrow();

        Assertions.assertEquals("PAID", paymentDTO.getStatus());
        Assertions.assertEquals(OrderStatus.PAID, updatedOrder.getStatus());
    }
}
