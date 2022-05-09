package com.getir.keycloakmock.service;

import com.getir.keycloakmock.document.User;
import com.getir.keycloakmock.exception.DuplicateUserException;
import com.getir.keycloakmock.exception.MissingUserException;
import com.getir.keycloakmock.mapper.UserMapper;
import com.getir.keycloakmock.model.UserDto;
import com.getir.keycloakmock.model.UserToken;
import com.getir.keycloakmock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserToken saveUser(UserDto user) {
        var result = userMapper.mapUserDtoToUser(user);

        User savedUser;

        try {
            savedUser = userRepository.save(result);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException("Could not create user, user with same username or email already exists");
        }

        return userMapper.mapUserToToken(savedUser);
    }

    public List<UserDto> getAllUsers() {
        return userMapper.mapAllUsersToDtos(userRepository.findAll());
    }

    public UserDto findUser(UUID id) {
        var user = fetchUser(id);

        return userMapper.mapUserToUserDto(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public UserToken fetchUserToken(UUID id) {
        var user = fetchUser(id);

        return userMapper.mapUserToToken(user);
    }

    private User fetchUser(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new MissingUserException(id)
        );
    }
}
