package com.example.demo.mapper;

import com.example.demo.Dto.OrderItemDTO;
import com.example.demo.model.Book;
import com.example.demo.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderItemMapperTest {

    private final OrderItemMapper mapper =
            Mappers.getMapper(OrderItemMapper.class);

    @Test
    void testToDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Java");

        OrderItem item = new OrderItem();
        item.setBook(book);
        item.setQuantity(5);
        item.setPrice(500.0);

        OrderItemDTO dto = mapper.toDto(item);

        assertEquals(1L, dto.getBookId());
        assertEquals("Java", dto.getBookName());
        assertEquals(5, dto.getQuantity());
        assertEquals(500.0, dto.getPrice());
    }

    @Test
    void testToEntity() {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setBookId(2L);
        dto.setQuantity(3);
        dto.setPrice(300.0);

        OrderItem entity = mapper.toEntity(dto);

        assertEquals(2L, entity.getBook().getId());
        assertEquals(3, entity.getQuantity());
        assertEquals(300.0, entity.getPrice());
    }

    @Test
    void testToDtoList() {
        OrderItem item = new OrderItem();
        item.setBook(new Book());

        List<OrderItemDTO> list =
                mapper.toDtoList(Collections.singletonList(item));

        assertEquals(1, list.size());
    }

    @Test
    void testToEntityList() {
        OrderItemDTO dto = new OrderItemDTO();

        List<OrderItem> list =
                mapper.toEntityList(Collections.singletonList(dto));

        assertEquals(1, list.size());
    }
}
