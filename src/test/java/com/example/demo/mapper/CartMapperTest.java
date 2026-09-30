package com.example.demo.mapper;

import com.example.demo.Dto.CartDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartMapperTest {


    @Autowired
    private CartMapper mapper;



    @Test
    void testToDto() {
        Book book = new Book();
        book.setTitle("Book");
        book.setPrice(50.0);

        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(2);

        Cart cart = new Cart();
        cart.setItems(Collections.singletonList(item));

        CartDTO dto = mapper.toDto(cart);

        assertEquals(100.0, dto.getTotalPrice());
        assertEquals(1, dto.getItems().size());
    }

    @Test
    void testToEntity() {
        CartDTO dto = new CartDTO();
        dto.setItems(Collections.emptyList());

        Cart cart = mapper.toEntity(dto);

        assertNotNull(cart);
    }

    @Test
    void testToDtoList() {
        Book book = new Book();
        book.setPrice(40.0);

        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(2);

        Cart cart = new Cart();
        cart.setItems(Collections.singletonList(item));

        List<CartDTO> list =
                mapper.toDtoList(Collections.singletonList(cart));

        assertEquals(1, list.size());
        assertEquals(80.0, list.get(0).getTotalPrice());
    }

    @Test
    void testToEntityList() {
        CartDTO dto = new CartDTO();

        List<Cart> list =
                mapper.toEntityList(Collections.singletonList(dto));

        assertEquals(1, list.size());
    }
}
