package com.example.demo.mapper;

import com.example.demo.Dto.BookDto;
import com.example.demo.model.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "category.name", target = "categoryName")
    BookDto toDto(Book book);

    @Mapping(target = "category", ignore = true)
    Book toEntity(BookDto bookDto);

    List<BookDto> toDtoList(List<Book> bookList);

    List<Book> toEntityList(List<BookDto> bookDtoList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromDto(BookDto dto, @MappingTarget Book entity);
}
