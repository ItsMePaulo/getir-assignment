package com.getir.bookstoreservice.service;

import com.getir.bookstoreservice.documents.Book;
import com.getir.bookstoreservice.documents.BookCartItem;
import com.getir.bookstoreservice.exception.BooksOutOfStockException;
import com.getir.bookstoreservice.exception.MissingBookException;
import com.getir.bookstoreservice.exception.MissingCartException;
import com.getir.bookstoreservice.mapper.UserCartMapper;
import com.getir.bookstoreservice.model.UserCartDto;
import com.getir.bookstoreservice.model.UserCartResponseDto;
import com.getir.bookstoreservice.repository.BookRepository;
import com.getir.bookstoreservice.repository.UserCartRepository;
import com.getir.ordersapi.clients.OrdersClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCartService {

    private final UserCartRepository userCartRepository;
    private final BookRepository bookRepository;
    private final UserCartMapper userCartMapper;
    private final OrdersClient ordersClient;

    public UserCartResponseDto addUserCart(UserCartDto userCartDto) {
        var bookCartItemMap = generateBookMap(userCartDto);
        var books = fetchBooks(bookCartItemMap);

        verifyBooks(bookCartItemMap.keySet(), books.stream().map(Book::getIsbn).collect(Collectors.toList()));

        var outOfStockBooks = findAnyOutOfStockBooks(bookCartItemMap, books);


        if (outOfStockBooks.isEmpty()) {
            var userCart = userCartMapper
                    .mapUserCartDtoToUserCart(userCartDto, sumPrices(books, bookCartItemMap));

            userCartRepository.save(userCart);

            return new UserCartResponseDto(
                    userCartDto,
                    true
            );
        }

        throw new BooksOutOfStockException(outOfStockBooks);
    }

    private void verifyBooks(Set<String> requestedBooks, List<String> discoveredBooks) {
        for (String requestedBook : requestedBooks) {
            if (!discoveredBooks.contains(requestedBook)) {
                throw new MissingBookException(requestedBook);
            }
        }
    }

    public UserCartResponseDto fetchUserCart(UUID cartId) {
        var cart = userCartRepository.findById(cartId).orElseThrow(() -> new MissingCartException(
                cartId
        ));

        return new UserCartResponseDto(
                userCartMapper.mapUserCartToUserCartDto(cart),
                checkIfStillValid(cart.getBooks())
        );
    }

    private Boolean checkIfStillValid(List<BookCartItem> books) {
        return books.stream().noneMatch(book -> {
            var foundBook = bookRepository.findById(book.getBookId()).orElseThrow(() -> new MissingBookException(book.getBookId()));

            return foundBook.getStock() < book.getAmount();
        });
    }

    private Double sumPrices(List<Book> books, Map<String, BookCartItem> bookCartItemMap) {
        var result = books.stream()
                .reduce(0.0,
                        (total, book) -> total += book.getPrice() * bookCartItemMap.get(book.getIsbn()).getAmount(),
                        Double::sum
                );

        return new BigDecimal(result).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private List<BookCartItem> findAnyOutOfStockBooks(Map<String, BookCartItem> bookCartItemMap, List<Book> books) {
        return books.stream()
                .filter(book -> {
                    var bookItem = bookCartItemMap.get(book.getIsbn());
                    return bookItem.getAmount() > book.getStock();
                })
                .map(book -> bookCartItemMap.get(book.getIsbn()))
                .collect(Collectors.toList());
    }

    private List<Book> fetchBooks(Map<String, BookCartItem> bookMap) {
        var discoveredBooks = bookRepository.findAllByIsbnIn(new ArrayList<>(bookMap.keySet()));

        if (discoveredBooks.isEmpty()) {
            throw new MissingBookException(bookMap.keySet().stream().findFirst().orElse(""));
        }

        return discoveredBooks;
    }

    private Map<String, BookCartItem> generateBookMap(UserCartDto userCartDto) {
        return userCartDto.getBooks().stream().collect(Collectors.toMap(
                BookCartItem::getBookId, bookCartItem -> bookCartItem
        ));
    }
}
