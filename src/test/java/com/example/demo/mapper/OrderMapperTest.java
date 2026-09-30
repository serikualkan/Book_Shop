package com.example.demo.mapper;

import com.example.demo.Dto.OrderDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private  OrderMapper mapper;

    @Test
    void testToDto() {
        OrderItem item = new OrderItem();
        item.setBook(new Book());

        Order order = new Order();
        order.setId(1L);
        order.setItems(Collections.singletonList(item));

        OrderDTO dto = mapper.toDto(order);

        assertEquals(1L, dto.getOrderId());
        assertEquals(1, dto.getItems().size());
    }

    @Test
    void testToEntity() {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(2L);

        Order order = mapper.toEntity(dto);

        assertEquals(2L, order.getId());
    }

    @Test
    void testToDtoList() {
        Order order = new Order();

        List<OrderDTO> list =
                mapper.toDtoList(Collections.singletonList(order));

        assertEquals(1, list.size());
    }

    @Test
    void testToEntityList() {
        OrderDTO dto = new OrderDTO();

        List<Order> list =
                mapper.toEntityList(Collections.singletonList(dto));

        assertEquals(1, list.size());
    }
}
