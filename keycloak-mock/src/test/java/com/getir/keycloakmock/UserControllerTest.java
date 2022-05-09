package com.getir.keycloakmock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.keycloakmock.model.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    public static final String TEST_EMAIL_COM = "test@email.com";
    public static final String USER_NAME = "Paul";
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<String, String, UserDto> userFactory = (name, email) -> new UserDto(
            name, email, "testPassword123"
    );

    @Test
    void shouldCreateUser() throws Exception {
        var userResponse = createUser();

        deleteUser(userResponse);
    }

    @Test
    void getAllUsers() throws Exception {
        var users = List.of(
                userFactory.apply("Paul", "test@email.com"),
                userFactory.apply("Paul2", "test2@gmail.com")
        );

        // create some users
        for (UserDto user : users) {
            mockMvc.perform(post("/user")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user))
            ).andExpect(status().isOk());
        }

        // fetch users
        var result = mockMvc.perform(get("/user").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<UserDto> retrievedUsers = mapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertTrue(retrievedUsers
                .stream()
                .map(UserDto::getUsername).collect(Collectors.toList())
                .containsAll(users.stream().map(UserDto::getUsername).collect(Collectors.toList()))
        );

        assertTrue(retrievedUsers
                .stream()
                .map(UserDto::getEmail).collect(Collectors.toList())
                .containsAll(users.stream().map(UserDto::getEmail).collect(Collectors.toList()))
        );

        // delete users
        for (UserDto user : retrievedUsers) {
            deleteUser(user);
        }
    }

    @Test
    void getSingleUser() throws Exception {
        var userResponse = createUser();

        var result = mockMvc.perform(get(String.format("/user/%s", userResponse.getId()))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals("Paul", response.getUsername());

        deleteUser(userResponse);
    }

    @Test
    void shouldThrowDuplicationException() throws Exception {
        var ogUser = createUser();

        var sameName = userFactory.apply(USER_NAME, "");
        var sameEmail = userFactory.apply("", TEST_EMAIL_COM);

       testBadRequestOnPost(sameName);
       testBadRequestOnPost(sameEmail);

       deleteUser(ogUser);
    }

    @Test
    void shouldThrowMissingUserException() throws Exception {
        // an unknown random id
        mockMvc.perform(get(String.format("/user/%s", UUID.randomUUID()))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    void testBadRequestOnPost(UserDto userDto) throws Exception {
        mockMvc.perform(post("/user").contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    private UserDto createUser() throws Exception {
        var user = userFactory.apply(USER_NAME, TEST_EMAIL_COM);

        var result = mockMvc.perform(post("/user").contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        var userResponse = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals(USER_NAME, userResponse.getUsername());

        return userResponse;
    }

    private void deleteUser(UserDto user) throws Exception {

        mockMvc.perform(delete(String.format("/user/%s", user.getId())))
                .andExpect(status().isOk());
    }
}