package com.example.demo.mapper;

import com.example.demo.Dto.CartItemDTO;
import com.example.demo.model.CartItem;
import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartItemMapperTest {

    private final CartItemMapper mapper =
            Mappers.getMapper(CartItemMapper.class);

    @Test
    void testToDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setPrice(100.0);

        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(2);

        CartItemDTO dto = mapper.toDto(item);

        assertEquals(1L, dto.getBookId());
        assertEquals("Clean Code", dto.getBookName());
        assertEquals(2, dto.getQuantity());
        assertEquals(200.0, dto.getPrice());
    }

    @Test
    void testToEntity() {
        CartItemDTO dto = new CartItemDTO();
        dto.setBookId(2L);
        dto.setQuantity(3);

        CartItem entity = mapper.toEntity(dto);

        assertEquals(2L, entity.getBook().getId());
        assertEquals(3, entity.getQuantity());
    }

    @Test
    void testToDtoList() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test");
        book.setPrice(50.0);

        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(2);

        List<CartItemDTO> list =
                mapper.toDtoList(Collections.singletonList(item));

        assertEquals(1, list.size());
        assertEquals(100.0, list.get(0).getPrice());
    }

    @Test
    void testToEntityList() {
        CartItemDTO dto = new CartItemDTO();
        dto.setBookId(5L);
        dto.setQuantity(1);

        List<CartItem> list = mapper.toEntityList(Collections.singletonList(dto));

        assertEquals(1, list.size());
        assertEquals(5L, list.get(0).getBook().getId());
    }
}
