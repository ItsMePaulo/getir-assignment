package com.getir.bookstoreservice.service;

import com.getir.bookstoreservice.exception.MissingBookException;
import com.getir.bookstoreservice.mapper.BookMapper;
import com.getir.bookstoreservice.model.BookDto;
import com.getir.bookstoreservice.model.BookStockDto;
import com.getir.bookstoreservice.repository.BookRepository;
import com.getir.common.exceptions.DuplicateUniqueKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookStoreService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> addBooks(List<BookDto> bookDto) {

       return bookDto.stream().map(this::addBook).collect(Collectors.toList());
    }

    public BookDto addBook(BookDto bookDto) {
        bookRepository.save(bookMapper.mapBookDtoToBook(bookDto));

        return bookDto;
    }

    public BookDto updateBookStock(String isbn, BookStockDto updateDto) {
        var book = bookRepository.findById(isbn).orElseThrow(() ->
                new MissingBookException(isbn)
        );

        book.setStock(updateDto.performMappingMethod(book.getStock()));

        var newBook = bookRepository.save(book);

        return bookMapper.mapBookToBookDto(newBook);
    }

    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
