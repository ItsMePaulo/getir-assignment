package com.getir.keycloakmock.controller;

import com.getir.keycloakmock.model.UserDto;
import com.getir.keycloakmock.model.UserToken;
import com.getir.keycloakmock.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    UserToken createUser(@RequestBody UserDto userDto) {
        log.info("Adding user: {}", userDto.getUsername());
        return userService.saveUser(userDto);
    }

}
