package com.example.demo.mapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.Dto.BookDto;
import com.example.demo.model.Book;

@SpringBootTest
class BookMapperTest {

    private final BookMapper bookMapper =
            Mappers.getMapper(BookMapper.class);

    @Test
    void testToDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setPrice(50.0);

        BookDto dto = bookMapper.toDto(book);

        assertEquals(book.getId(), dto.getId());
        assertEquals(book.getTitle(), dto.getTitle());
        assertEquals(book.getAuthor(), dto.getAuthor());
        assertEquals(book.getPrice(), dto.getPrice());
    }

    @Test
    void testToEntity() {
        BookDto dto = new BookDto();
        dto.setId(2L);
        dto.setTitle("Java");
        dto.setAuthor("Oracle");
        dto.setPrice(60.0);

        Book entity = bookMapper.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getAuthor(), entity.getAuthor());
        assertEquals(dto.getPrice(), entity.getPrice());
    }

    @Test
    void testToDtoList() {
        Book book = new Book();
        book.setId(3L);

        List<BookDto> dtoList =
                bookMapper.toDtoList(Collections.singletonList(book));

        assertEquals(1, dtoList.size());
        assertEquals(book.getId(), dtoList.get(0).getId());
    }

    @Test
    void testToEntityList() {
        BookDto dto = new BookDto();
        dto.setId(4L);

        List<Book> entityList =
                bookMapper.toEntityList(Collections.singletonList(dto));

        assertEquals(1, entityList.size());
        assertEquals(dto.getId(), entityList.get(0).getId());
    }
}
