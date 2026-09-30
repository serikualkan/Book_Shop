package com.example.demo.service;

import com.example.demo.Dto.CartDTO;
import com.example.demo.Dto.OrderDTO;

public interface CartService {

    void addToCart(String email, Long catId);

    CartDTO getCart(String email);

    OrderDTO checkout(String email);
}