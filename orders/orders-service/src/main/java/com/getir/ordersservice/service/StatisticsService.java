package com.getir.ordersservice.service;

import com.getir.ordersservice.documents.Order;
import com.getir.ordersservice.documents.ProductItem;
import com.getir.ordersservice.model.StatisticsDto;
import com.getir.ordersservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrderRepository orderRepository;

    public Map<Month, StatisticsDto> fetchGlobalStatistics(
            @NonNull LocalDateTime fromDate,
            @NonNull LocalDateTime toDate
    ) {
        var orders = orderRepository.fetchGlobalCompleteOrdersBetweenDates(fromDate, toDate);

        return fetchStatisticsFromOrders(orders);
    }

    public Map<Month, StatisticsDto> fetchUserStatistics(
            @NonNull UUID userId,
            @NonNull LocalDateTime fromDate,
            @NonNull LocalDateTime toDate
    ) {
        var orders = orderRepository.fetchUserCompleteOrdersBetweenDates(userId, fromDate, toDate);

        return fetchStatisticsFromOrders(orders);
    }

    private Map<Month, StatisticsDto> fetchStatisticsFromOrders(List<Order> orders) {
        var monthOrderMap = orders.stream()
                .collect(groupingBy(order -> order.getLastUpdatedAt().getMonth()));

        return monthOrderMap.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, ordersByMonth -> mapOrdersToStatisticsDto(ordersByMonth.getValue())
        ));
    }

    private StatisticsDto mapOrdersToStatisticsDto(List<Order> orders) {

        var totalAmount = orders.stream().mapToDouble(Order::getTotalPrice).sum();
        var totalProducts = orders.stream().mapToInt(order -> sumProducts(order.getProducts())).sum();

        return new StatisticsDto(
                orders.size(),
                totalProducts,
                totalAmount
        );
    }

    private int sumProducts(List<ProductItem> products) {
        return products.stream().mapToInt(ProductItem::getAmount).sum();
    }
}
