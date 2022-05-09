package com.getir.keycloakmock.service;

import com.getir.keycloakmock.document.User;
import com.getir.keycloakmock.model.UserDto;
import com.getir.keycloakmock.model.UserToken;
import com.getir.keycloakmock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserToken saveUser(UserDto user) {
        var result = new User(
                UUID.randomUUID(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );

        var savedUser = userRepository.save(result);

        return new UserToken(savedUser.getId(), savedUser.getName());
    }

}
