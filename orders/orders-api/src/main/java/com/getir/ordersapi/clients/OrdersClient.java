package com.getir.ordersapi.clients;

import com.getir.ordersapi.model.OrderStatus;
import com.getir.ordersapi.model.OrdersDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@FeignClient(value = "orders", url = "${orders.url:localhost:8002/orders}", primary = false)
public interface OrdersClient {

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<OrdersDto> createOrder(@RequestBody @Valid OrdersDto ordersDto);

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<OrdersDto> updateOrder(@RequestBody @Valid OrdersDto ordersDto);

    @PatchMapping(value = "/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<OrdersDto> updateOrderStatus(
            @PathVariable("orderId") UUID id,
            @RequestParam("status") OrderStatus orderStatus
    );

    @GetMapping
    ResponseEntity<List<OrdersDto>> fetchAllOrders();


    @GetMapping("/{orderId}")
    ResponseEntity<OrdersDto> fetchOrder(@PathVariable("orderId") UUID id);

    @DeleteMapping(value = "/{orderId}")
    ResponseEntity<String> deleteOrder(@PathVariable("orderId") UUID id);
}
