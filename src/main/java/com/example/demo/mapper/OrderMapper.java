package com.example.demo.mapper;

import com.example.demo.Dto.OrderDTO;
import com.example.demo.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = OrderItemMapper.class
)
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    OrderDTO toDto(Order order);

    @Mapping(source = "orderId", target = "id")
    Order toEntity(OrderDTO dto);

    @Mapping(source = "id", target = "orderId")
    List<OrderDTO> toDtoList(List<Order> orders);

    List<Order> toEntityList(List<OrderDTO> dtos);
}
