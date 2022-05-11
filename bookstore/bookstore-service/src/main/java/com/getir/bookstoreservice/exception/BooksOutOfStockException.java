package com.getir.bookstoreservice.exception;

import com.getir.bookstoreservice.documents.BookCartItem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BooksOutOfStockException extends RuntimeException {

    private final static Function<List<BookCartItem>, String> message = (outOfStockList) -> String.format(
            "Could not create cart some books where unavailable: %s", mapOutOfStockItems(outOfStockList)
    );

    private static String mapOutOfStockItems(List<BookCartItem> outOfStockList) {
        return outOfStockList
                .stream()
                .map(book -> String.format("There were less than %s items in stock fo book: %s", book.getAmount(), book.getBookId()))
                .collect(Collectors.joining(","));
    }

    public BooksOutOfStockException(List<BookCartItem> outOfStockBooks) {
        super(message.apply(outOfStockBooks));
    }
}
