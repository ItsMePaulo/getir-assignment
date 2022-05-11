package com.getir.bookstoreapi.clients;

import com.getir.bookstoreapi.model.BookDto;
import com.getir.bookstoreapi.model.BookStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "books", url = "${bookstore.url:localhost:8001/books}", primary = false)
public interface BookstoreClient {

    @PostMapping
    ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto);

    @PostMapping(value = "/batch", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<BookDto>> createBatchBook(@RequestBody List<BookDto> bookDto);

    @PatchMapping(value = "/{bookId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<BookDto> updateBookStock(@PathVariable("bookId") String isbn, @RequestBody BookStockDto updateDto);

    @GetMapping
    ResponseEntity<List<BookDto>> getAllBooks();

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> fetchBook(@PathVariable("bookId") String bookId);

    @DeleteMapping(value = "/{bookId}")
    ResponseEntity<String> deleteBook(@PathVariable("bookId") String isbn);
}
