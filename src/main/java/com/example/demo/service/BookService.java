package com.example.demo.service;

import com.example.demo.Dto.BookDto;
import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto create(BookDto dto);

    BookDto update(Long id, BookDto dto);

    void delete(Long id);
}
