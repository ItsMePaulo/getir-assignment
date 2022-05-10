package com.getir.bookstoreservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.bookstoreservice.model.BookDto;
import com.getir.bookstoreservice.model.BookStockDto;
import com.getir.bookstoreservice.model.BookStockMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.function.BiFunction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    public static final String TEST_ISBN = "12345";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private final BiFunction<String, String, BookDto> bookFactory = (isbn, title) -> new BookDto(
            isbn, title, "Edition 2", 50, 24.99
    );

    @Test
    void shouldCreateBook() throws Exception {
        var book = createBook();

        deleteBook(book.getIsbn());
    }

    @Test
    void shouldCreateBatchBooks() throws Exception {
        var books = List.of(
                bookFactory.apply("12345", "Harry Potter"),
                bookFactory.apply("67879", "The Golden Compass")
        );

        var booksAsJsonString = mapper.writeValueAsString(books);

        // create books
        mockMvc.perform(post("/books/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(booksAsJsonString)
                ).andExpect(status().isOk())
                .andExpect(content().json(booksAsJsonString));

        // delete all books
        for (BookDto book : books) {
            deleteBook(book.getIsbn());
        }
    }

    @Test
    void shouldAddStock() throws Exception {
        var book = createBook();

        // add 10 to stock
        book.setQuantity(book.getQuantity() + 10);

        var bookStockDto = new BookStockDto(BookStockMethod.ADD, 10);
        var bookStockAsString = mapper.writeValueAsString(bookStockDto);

        mockMvc.perform(patch(String.format("/books/%s", book.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookStockAsString)
                ).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));

        deleteBook(book.getIsbn());
    }

    @Test
    void shouldSubtractStock() throws Exception {
        var book = createBook();

        // subtracts 10 from stock
        book.setQuantity(book.getQuantity() - 10);

        var bookStockDto = new BookStockDto(BookStockMethod.SUBTRACT, 10);
        var bookStockAsString = mapper.writeValueAsString(bookStockDto);

        mockMvc.perform(patch(String.format("/books/%s", book.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookStockAsString)
                ).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));

        deleteBook(book.getIsbn());
    }

    @Test
    void shouldReturnBookNotFound() throws Exception {
        var bookStockDto = new BookStockDto(BookStockMethod.SUBTRACT, 10);
        var bookStockAsString = mapper.writeValueAsString(bookStockDto);

        mockMvc.perform(patch("/books/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookStockAsString)
                ).andExpect(status().isNotFound());
    }

    private BookDto createBook() throws Exception {
        var bookDto = bookFactory.apply(TEST_ISBN, "Harry Potter");

        var bookAsJsonString = mapper.writeValueAsString(bookDto);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAsJsonString)
                ).andExpect(status().isOk())
                .andExpect(content().json(bookAsJsonString));

        return bookDto;
    }

    private void deleteBook(String isbn) throws Exception {
        mockMvc.perform(delete(String.format("/books/%s", isbn)))
                .andExpect(status().isOk());
    }

}
