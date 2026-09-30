package com.example.demo.service;

import com.example.demo.Dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void getMyOrders() {
        User user = new User();
        user.setEmail("orders@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Order order1 = new Order();
        order1.setUser(user);
        order1.setStatus(OrderStatus.NEW);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setStatus(OrderStatus.PAID);
        orderRepository.save(order2);

        List<OrderDTO> orders = orderService.getMyOrders("orders@test.com");

        Assertions.assertEquals(2, orders.size());
    }


    @Test
    void getAllOrders() {
        User user = new User();
        user.setEmail("all@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Order order1 = new Order();
        order1.setUser(user);
        order1.setStatus(OrderStatus.NEW);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setStatus(OrderStatus.PAID);
        orderRepository.save(order2);

        List<OrderDTO> orders = orderService.getAllOrders();

        Assertions.assertTrue(orders.size() >= 2);
    }


    @Test
    void updateStatus() {
        User user = new User();
        user.setEmail("status@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);

        OrderDTO updated = orderService.updateStatus(
                order.getId(),
                OrderStatus.PAID
        );

        Assertions.assertEquals(OrderStatus.PAID, updated.getStatus());
    }


    @Test
    void getEntity() {
        User user = new User();
        user.setEmail("entity@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);

        Order found = orderService.getEntity(order.getId());

        Assertions.assertEquals(order.getId(), found.getId());
        Assertions.assertEquals(OrderStatus.NEW, found.getStatus());
    }

}
