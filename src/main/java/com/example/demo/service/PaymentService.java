package com.example.demo.service;

import com.example.demo.Dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO pay(Long orderId);
}