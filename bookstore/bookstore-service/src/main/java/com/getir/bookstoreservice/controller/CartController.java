package com.getir.bookstoreservice.controller;

import com.getir.bookstoreservice.model.UserCartDto;
import com.getir.bookstoreservice.model.UserCartResponseDto;
import com.getir.bookstoreservice.service.UserCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class CartController {

    private final UserCartService userCartService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<UserCartResponseDto> createUserCart(@RequestBody @Valid UserCartDto userCartDto) {
        log.info("Adding/Updating a user cart for user: {}", userCartDto.getUserId());

        return ResponseEntity.ok(userCartService.addUserCart(userCartDto));
    }

    @GetMapping(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserCartResponseDto> fetchUserCart(@PathVariable("cartId") UUID cartId) {
        log.info("Fetching user cart: {}", cartId);

        return ResponseEntity.ok(userCartService.fetchUserCart(cartId));
    }
}
