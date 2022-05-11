package com.getir.bookstoreservice.service;

import com.getir.bookstoreservice.documents.Book;
import com.getir.bookstoreservice.documents.BookCartItem;
import com.getir.bookstoreservice.exception.BooksOutOfStockException;
import com.getir.bookstoreservice.mapper.UserCartMapper;
import com.getir.bookstoreservice.model.UserCartDto;
import com.getir.bookstoreservice.model.UserCartResponseDto;
import com.getir.bookstoreservice.repository.BookRepository;
import com.getir.bookstoreservice.repository.UserCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCartService {

    private final UserCartRepository userCartRepository;
    private final BookRepository bookRepository;
    private final UserCartMapper userCartMapper;

    public UserCartResponseDto addUserCart(UserCartDto userCartDto) {
        var bookCartItemMap = generateBookMap(userCartDto);
        var books = fetchBooks(bookCartItemMap);

        var outOfStockBooks = findAnyOutOfStockBooks(bookCartItemMap, books);

        if (outOfStockBooks.isEmpty()) {
            var userCart = userCartMapper.mapUserCartDtoToUserCart(userCartDto);
            userCartRepository.save(userCart);

            return new UserCartResponseDto(
                    userCartDto,
                    true,
                    sumPrices(books, bookCartItemMap)
            );
        }

        throw new BooksOutOfStockException(outOfStockBooks);
    }

    private Double sumPrices(List<Book> books, Map<String, BookCartItem> bookCartItemMap) {
        return books.stream()
                .reduce(0.0,
                        (total, book) -> total += book.getPrice() * bookCartItemMap.get(book.getIsbn()).getAmount(),
                        Double::sum
                );
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
        return bookRepository.findAllByIsbnIn(new ArrayList<>(bookMap.keySet()));
    }

    private Map<String, BookCartItem> generateBookMap(UserCartDto userCartDto) {
        return userCartDto.getBooks().stream().collect(Collectors.toMap(
                BookCartItem::getBookId, bookCartItem -> bookCartItem
        ));
    }
}
