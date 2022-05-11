package com.getir.ordersservice.mapper;

import com.getir.ordersservice.documents.Order;
import com.getir.ordersapi.model.OrdersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrdersMapper {


    @Mappings({
            @Mapping(target = "id", expression = "java(generateId())"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastUpdatedAt", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    Order mapOrderDtoToOrder(OrdersDto ordersDto);

    OrdersDto mapOrderToOrdersDto(Order order);

    default UUID generateId() {
        return UUID.randomUUID();
    }
}
