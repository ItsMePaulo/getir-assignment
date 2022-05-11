package com.getir.ordersservice.service;

import com.getir.ordersservice.documents.Order;
import com.getir.ordersservice.exception.MissingOrderException;
import com.getir.ordersservice.mapper.OrdersMapper;
import com.getir.ordersservice.model.OrderStatus;
import com.getir.ordersservice.model.OrdersDto;
import com.getir.ordersservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrdersMapper ordersMapper;

    public OrdersDto createOrder(OrdersDto ordersDto) {
        var order = orderRepository.save(ordersMapper.mapOrderDtoToOrder(ordersDto));

        return ordersMapper.mapOrderToOrdersDto(order);
    }

    public OrdersDto updateOrderStatus(UUID id, OrderStatus orderStatus) {
        var order = fetchOrderById(id);

        order.setStatus(orderStatus);
        order.setLastUpdatedAt(LocalDateTime.now());
        var newOrder = orderRepository.save(order);

        return ordersMapper.mapOrderToOrdersDto(newOrder);
    }

    public OrdersDto fetchOrder(UUID id) {
        var order = fetchOrderById(id);

        return ordersMapper.mapOrderToOrdersDto(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    private Order fetchOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new MissingOrderException(id)
        );
    }
}
