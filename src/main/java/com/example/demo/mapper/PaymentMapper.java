package com.example.demo.mapper;

import com.example.demo.Dto.PaymentDTO;
import com.example.demo.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "id", target = "paymentId")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "paymentId", target = "id")
    Payment toEntity(PaymentDTO dto);

    List<PaymentDTO> toDtoList(List<Payment> payments);
    List<Payment> toEntityList(List<PaymentDTO> dtos);
}
