package com.getir.bookstoreservice.controller;

import com.getir.bookstoreapi.clients.CartClient;
import com.getir.bookstoreapi.model.UserCartDto;
import com.getir.bookstoreapi.model.UserCartResponseDto;
import com.getir.bookstoreservice.service.UserCartService;
import com.getir.ordersapi.model.AddressDto;
import com.getir.ordersapi.model.OrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController implements CartClient {

    private final UserCartService userCartService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<UserCartResponseDto> createUserCart(@RequestBody @Valid UserCartDto userCartDto) {
        log.info("Adding/Updating a user cart for user: {}", userCartDto.getUserId());

        return ResponseEntity.ok(userCartService.addUserCart(userCartDto));
    }

    @GetMapping(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCartResponseDto> fetchUserCart(@PathVariable("cartId") UUID cartId) {
        log.info("Fetching user cart: {}", cartId);

        return ResponseEntity.ok(userCartService.fetchUserCartDto(cartId));
    }

    @PostMapping(value = "/checkout/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdersDto> checkout(@PathVariable("cartId") UUID cartId, @RequestBody AddressDto addressDto) {
        log.info("Creating order from cart for user: {}", cartId);

        return ResponseEntity.ok(userCartService.checkoutCart(cartId, addressDto));
    }
}
