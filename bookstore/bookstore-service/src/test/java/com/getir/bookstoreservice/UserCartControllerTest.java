package com.getir.bookstoreservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.bookstoreapi.model.BookCartItem;
import com.getir.bookstoreapi.model.BookDto;
import com.getir.bookstoreapi.model.UserCartDto;
import com.getir.bookstoreapi.model.UserCartResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserCartControllerTest {

    public static final String BOOK_ID_1 = "12345";
    public static final String BOOK_ID_2 = "6789";
    public static final double BOOK_PRICE_1 = 24.99;
    public static final double BOOK_PRICE_2 = 50.0;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    BiFunction<Integer, Integer, UserCartDto> userCartFactory = (quantity1, quantity2) -> new UserCartDto(
            UUID.fromString("66db3fc7-eb44-48cf-a955-7122992f6e65"), "Paul", List.of(
            new BookCartItem(BOOK_ID_1, quantity1),
            new BookCartItem(BOOK_ID_2, quantity2)
    ), 10 * BOOK_PRICE_1 + 4 * BOOK_PRICE_2);


    private final List<BookDto> books = List.of(
            new BookDto(
                    BOOK_ID_1, "Harry Potter", "Edition 2", 50, BOOK_PRICE_1
            ),
            new BookDto(
                    BOOK_ID_2, "Golden Compass", "", 10, BOOK_PRICE_2
            )
    );

    @BeforeEach
    public void setUpBooks() throws Exception {

        var bookAsJsonString = mapper.writeValueAsString(books);

        mockMvc.perform(post("/books/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAsJsonString)
                ).andExpect(status().isOk())
                .andExpect(content().json(bookAsJsonString));
    }

    @Test
    void shouldCreateUserCart() throws Exception {
        var cart = userCartFactory.apply(10, 4);
        var cartResponse = new UserCartResponseDto(
                cart, true
        );

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cart))
                ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(cartResponse)));
    }

    @Test
    void shouldThrowOutOfStockException() throws Exception {
        var cart = userCartFactory.apply(1000, 4);

        mockMvc.perform(post("/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cart))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowInvalidMethodDtoException() throws Exception {
        var cart = userCartFactory.apply(-1, 4);

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cart))
                ).andExpect(status().isBadRequest());
    }

    @AfterEach
    void deleteBooks() throws Exception {

        for (String bookId : List.of(BOOK_ID_1, BOOK_ID_2)) {
            mockMvc.perform(delete(String.format("/books/%s", bookId))).andExpect(status().isOk());
        }
    }
}
