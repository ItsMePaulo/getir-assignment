package com.getir.bookstoreservice.controller;

import com.getir.bookstoreservice.model.UserCartDto;
import com.getir.bookstoreservice.model.UserCartResponseDto;
import com.getir.bookstoreservice.service.UserCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final UserCartService userCartService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<UserCartResponseDto> createBook(@RequestBody @Valid UserCartDto userCartDto) {
        log.info("Adding/Updating a user cart for user: {}", userCartDto.getUserId());

        return ResponseEntity.ok(userCartService.addUserCart(userCartDto));
    }
}
