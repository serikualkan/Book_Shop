package com.example.demo.mapper;

import com.example.demo.Dto.PaymentDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentMapperTest {

    private final PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void testToDto() {
        Order order = new Order();
        order.setId(1L);

        Payment payment = new Payment();
        payment.setId(10L);
        payment.setOrder(order);

        PaymentDTO dto = mapper.toDto(payment);
        assertEquals(payment.getId(), dto.getPaymentId());
        assertEquals(order.getId(), dto.getOrderId());
    }

    @Test
    void testToEntity() {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(20L);
        dto.setOrderId(2L);

        Payment entity = mapper.toEntity(dto);
        assertEquals(dto.getPaymentId(), entity.getId());
        assertEquals(dto.getOrderId(), entity.getOrder().getId());
    }

    @Test
    void testToDtoList() {
        Payment payment = new Payment();
        List<PaymentDTO> dtoList = mapper.toDtoList(Collections.singletonList(payment));
        assertEquals(1, dtoList.size());
    }

    @Test
    void testToEntityList() {
        PaymentDTO dto = new PaymentDTO();
        List<Payment> entityList = mapper.toEntityList(Collections.singletonList(dto));
        assertEquals(1, entityList.size());
    }
}
