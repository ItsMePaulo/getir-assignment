package com.getir.keycloakmock.controller;

import com.getir.keycloakmock.model.UserDto;
import com.getir.keycloakmock.model.UserToken;
import com.getir.keycloakmock.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    List<UserDto> getUsers() {
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    @GetMapping(value = "/token/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    UserToken fetchUserToken(@PathVariable("userId") UUID id) {
        log.info("Fetching user: {}", id);
        return userService.fetchUserToken(id);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    UserDto findUser(@PathVariable("userId") UUID id) {
        log.info("Fetching user: {}", id);
        return userService.findUser(id);
    }

    @DeleteMapping(value = "/{userId}")
    void deleteUser(@PathVariable("userId") UUID id) {
        log.info("Deleting user: {}", id);
        userService.deleteUser(id);
    }
}
