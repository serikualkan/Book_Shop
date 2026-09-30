package com.example.demo.mapper;

import com.example.demo.Dto.CartItemDTO;
import com.example.demo.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookName")
    @Mapping(
            expression = "java(item.getBook().getPrice() * item.getQuantity())",
            target = "price"
    )
    CartItemDTO toDto(CartItem item);

    @Mapping(source = "bookId", target = "book.id")
    CartItem toEntity(CartItemDTO dto);


    List<CartItemDTO> toDtoList(List<CartItem> items);

    List<CartItem> toEntityList(List<CartItemDTO> dtos);
}
