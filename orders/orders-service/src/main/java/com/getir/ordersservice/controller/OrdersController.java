package com.getir.ordersservice.controller;

import com.getir.ordersapi.clients.OrdersClient;
import com.getir.ordersapi.model.ProductItemDto;
import com.getir.ordersservice.documents.ProductItem;
import com.getir.ordersapi.model.OrderStatus;
import com.getir.ordersapi.model.OrdersDto;
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
public class OrdersController implements OrdersClient {

    private final OrderService orderService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<OrdersDto> createOrder(@RequestBody @Valid OrdersDto ordersDto) {
        log.info("Creating order new for items: {}", ordersDto.getProducts().stream().map(ProductItemDto::getProductId));

        return ResponseEntity.ok(orderService.createOrder(ordersDto));
    }

    @PatchMapping(value = "/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrdersDto> updateOrderStatus(
            @PathVariable("orderId") UUID id,
            @RequestParam("status") OrderStatus orderStatus
    ) {
        log.info("Updating status of order: {} to {}", id, orderStatus);

        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersDto> fetchOrder(@PathVariable("orderId") UUID id) {
        log.info("Fetching order: {}", id);

        return ResponseEntity.ok(orderService.fetchOrder(id));
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") UUID id) {
        log.info("Deleting order: {}", id);

        orderService.deleteOrder(id);
        return ResponseEntity.ok(String.format("Deleted order: %s", id));
    }

}
