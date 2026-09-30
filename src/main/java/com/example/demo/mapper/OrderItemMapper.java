package com.example.demo.mapper;

import com.example.demo.Dto.OrderItemDTO;
import com.example.demo.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookName")
    OrderItemDTO toDto(OrderItem item);

    @Mapping(source = "bookId", target = "book.id")
    OrderItem toEntity(OrderItemDTO dto);

    List<OrderItemDTO> toDtoList(List<OrderItem> items);

    List<OrderItem> toEntityList(List<OrderItemDTO> dtos);
}


