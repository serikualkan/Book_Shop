package com.example.demo.service.impl;

import com.example.demo.Dto.BookDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Book;
import com.example.demo.model.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAll() {
        return bookMapper.toDtoList(bookRepository.findAll());
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto create(BookDto dto) {
        Book book = bookMapper.toEntity(dto);

        setCategoryIfPresent(book, dto.getCategoryName());

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto update(Long id, BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookMapper.updateEntityFromDto(dto, book);

        setCategoryIfPresent(book, dto.getCategoryName());

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private void setCategoryIfPresent(Book book, String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) return;

        String clean = categoryName.trim();

        Category category = categoryRepository.findByNameIgnoreCase(clean)
                .orElseGet(() -> categoryRepository.save(new Category(null, clean)));

        book.setCategory(category);
    }
}
