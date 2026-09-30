package com.example.demo.service;

import com.example.demo.Dto.CartDTO;
import com.example.demo.Dto.OrderDTO;
import com.example.demo.model.Book;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void addToCart_and_getCart() {
        User user = new User();
        user.setEmail("cart@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setPrice(5000.0);
        bookRepository.save(book);

        cartService.addToCart("cart@test.com", book.getId());

        CartDTO cartDTO = cartService.getCart("cart@test.com");

        Assertions.assertEquals(1, cartDTO.getItems().size());
        Assertions.assertEquals(1, cartDTO.getItems().get(0).getQuantity());
    }

    @Test
    void checkout_success() {
        User user = new User();
        user.setEmail("checkout@test.com");
        user.setPassword("123");
        userRepository.save(user);

        Book book = new Book();
        book.setTitle("Checkout Book");
        book.setPrice(7000.0);
        bookRepository.save(book);

        cartService.addToCart("checkout@test.com", book.getId());

        OrderDTO orderDTO = cartService.checkout("checkout@test.com");

        Assertions.assertEquals(7000.0, orderDTO.getTotalPrice());
        Assertions.assertEquals(OrderStatus.NEW, orderDTO.getStatus());
    }
}


