package com.getir.bookstoreapi.clients;


import com.getir.bookstoreapi.model.UserCartDto;
import com.getir.bookstoreapi.model.UserCartResponseDto;
import com.getir.ordersapi.model.AddressDto;
import com.getir.ordersapi.model.OrdersDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.UUID;

@FeignClient(value = "cart", url = "${bookstore.url:localhost:8001/carts}", primary = false)
public interface CartClient {


    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<UserCartResponseDto> createUserCart(@RequestBody @Valid UserCartDto userCartDto);

    @GetMapping(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserCartResponseDto> fetchUserCart(@PathVariable("cartId") UUID cartId);

    @PostMapping(value = "/checkout/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OrdersDto> checkout(@PathVariable("cartId") UUID cartId, @RequestBody AddressDto addressDto);
}
