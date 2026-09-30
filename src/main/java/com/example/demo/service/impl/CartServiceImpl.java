package com.example.demo.service.impl;

import com.example.demo.Dto.CartDTO;
import com.example.demo.Dto.OrderDTO;
import com.example.demo.mapper.CartMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.*;
import com.example.demo.model.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    @Override
    public void addToCart(String email, Long bookId) {

        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found");

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(
                        new Cart(null, user, new ArrayList<>())
                ));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        cart.getItems().stream()
                .filter(i -> i.getBook().getId().equals(bookId))
                .findFirst()
                .ifPresentOrElse(
                        i -> i.setQuantity(i.getQuantity() + 1),
                        () -> cart.getItems().add(
                                new CartItem(null, cart, book, 1)
                        )
                );

        cartRepository.save(cart);
    }


    @Override
    public CartDTO getCart(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found");

        Cart cart = cartRepository.findByUser(user)
                .orElse(new Cart(null, user, new ArrayList<>()));

        return cartMapper.toDto(cart);
    }

    @Override
    public OrderDTO checkout(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found");

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart empty"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = cart.getItems().stream()
                .map(ci -> new OrderItem(
                        null,
                        ci.getBook(),
                        ci.getQuantity(),
                        ci.getBook().getPrice() * ci.getQuantity()
                )).toList();

        order.setItems(items);
        order.setTotalPrice(items.stream().mapToDouble(OrderItem::getPrice).sum());

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toDto(savedOrder);
    }
}
