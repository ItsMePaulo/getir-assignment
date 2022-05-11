package com.getir.ordersservice.controller;

import com.getir.ordersservice.documents.ProductItem;
import com.getir.ordersservice.model.OrderStatus;
import com.getir.ordersservice.model.OrdersDto;
import com.getir.ordersservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<OrdersDto> createOrder(@RequestBody @Valid OrdersDto ordersDto) {
        log.info("Creating order new for items: {}", ordersDto.getProducts().stream().map(ProductItem::getProductId));

        return ResponseEntity.ok(orderService.createOrder(ordersDto));
    }

    @PatchMapping(value = "/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<OrdersDto> updateOrderStatus(
            @PathVariable("orderId") UUID id,
            @RequestParam("status") OrderStatus orderStatus
    ) {
        log.info("Updating status of order: {} to {}", id, orderStatus);

        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrdersDto> fetchOrder(@PathVariable("orderId") UUID id) {
        log.info("Fetching order: {}", id);

        return ResponseEntity.ok(orderService.fetchOrder(id));
    }

    @DeleteMapping(value = "/{orderId}")
    ResponseEntity<String> deleteOrder(@PathVariable("orderId") UUID id) {
        log.info("Deleting order: {}", id);

        orderService.deleteOrder(id);
        return ResponseEntity.ok(String.format("Deleted order: %s", id));
    }

}
