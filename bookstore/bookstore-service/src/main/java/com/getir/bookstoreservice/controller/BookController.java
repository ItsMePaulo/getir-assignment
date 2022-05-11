package com.getir.bookstoreservice.controller;

import com.getir.bookstoreservice.model.BookDto;
import com.getir.bookstoreservice.model.BookStockDto;
import com.getir.bookstoreservice.service.BookStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookStoreService bookStoreService;

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        log.info("Adding/Updating new book: {} to warehouse", bookDto.getIsbn());

        return ResponseEntity.ok(bookStoreService.addBook(bookDto));
    }

    @PostMapping(value = "/batch", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<BookDto>> createBatchBook(@RequestBody List<BookDto> bookDto) {
        log.info("Adding books {} to warehouse", bookDto.stream().map(BookDto::getIsbn).collect(Collectors.toList()));

        return ResponseEntity.ok(bookStoreService.addBooks(bookDto));
    }

    @PatchMapping(value = "/{bookId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<BookDto> updateBookStock(@PathVariable("bookId") String isbn, @RequestBody BookStockDto updateDto) {
        log.info("Updating stock for book: {}", isbn);

        return ResponseEntity.ok(bookStoreService.updateBookStock(isbn, updateDto));
    }

    @GetMapping
    ResponseEntity<List<BookDto>> getAllBooks() {
        log.info("Fetching all books");

        return ResponseEntity.ok(bookStoreService.fetchAllBooks());
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> fetchBook(@PathVariable("bookId") String bookId) {
        log.info("Fetching book: {}", bookId);

        return ResponseEntity.ok(bookStoreService.fetchBook(bookId));
    }

    @DeleteMapping(value = "/{bookId}")
    ResponseEntity<String> deleteBook(@PathVariable("bookId") String isbn) {
        log.info("Deleting book: {}", isbn);

        bookStoreService.deleteBook(isbn);
        return ResponseEntity.ok(String.format("Deleted book: %s", isbn));
    }

}
