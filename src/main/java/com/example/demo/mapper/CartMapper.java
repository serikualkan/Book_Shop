package com.example.demo.mapper;

import com.example.demo.Dto.CartDTO;
import com.example.demo.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    @Mapping(
            target = "totalPrice",
            expression = "java(cart.getItems().stream()" +
                    ".mapToDouble(i -> i.getBook().getPrice() * i.getQuantity())" +
                    ".sum())"
    )
    CartDTO toDto(Cart cart);

    Cart toEntity(CartDTO dto);


    List<CartDTO> toDtoList(List<Cart> carts);

    List<Cart> toEntityList(List<CartDTO> dtos);
}

