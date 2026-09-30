package com.example.demo.service;

import com.example.demo.Dto.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void create_and_getById() {
        BookDto dto = new BookDto();
        dto.setTitle("Clean Code");
        dto.setAuthor("Robert Martin");
        dto.setPrice(6000.0);
        dto.setImageUrl("img.png");

        BookDto saved = bookService.create(dto);
        BookDto found = bookService.getById(saved.getId());

        Assertions.assertEquals(dto.getTitle(), found.getTitle());
        Assertions.assertEquals(dto.getAuthor(), found.getAuthor());
        Assertions.assertEquals(dto.getPrice(), found.getPrice());
        Assertions.assertEquals(dto.getImageUrl(), found.getImageUrl());
    }

    @Test
    void getAll_with_existing_data() {
        int initialSize = bookService.getAll().size();

        BookDto b1 = new BookDto();
        b1.setTitle("Book 1");
        b1.setPrice(4000.0);

        BookDto b2 = new BookDto();
        b2.setTitle("Book 2");
        b2.setPrice(5000.0);

        bookService.create(b1);
        bookService.create(b2);

        Assertions.assertEquals(initialSize + 2, bookService.getAll().size());
    }

    @Test
    void update_book() {
        BookDto dto = new BookDto();
        dto.setTitle("Old");
        dto.setPrice(3000.0);

        BookDto saved = bookService.create(dto);

        BookDto update = new BookDto();
        update.setTitle("New");
        update.setPrice(5500.0);

        BookDto updated = bookService.update(saved.getId(), update);

        Assertions.assertEquals(update.getTitle(), updated.getTitle());
        Assertions.assertEquals(update.getPrice(), updated.getPrice());
    }

    @Test
    void delete_book() {
        BookDto dto = new BookDto();
        dto.setTitle("Delete me");
        dto.setPrice(2000.0);

        BookDto saved = bookService.create(dto);
        Long id = saved.getId();

        bookService.delete(id);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> bookService.getById(id)
        );
    }
}
