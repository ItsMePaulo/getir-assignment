package com.getir.bookstoreservice.mapper;

import com.getir.bookstoreservice.documents.Book;
import com.getir.bookstoreservice.model.BookDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookDto> mapAllBooksToDtos(List<Book> allBooks);

    @Mapping(target = "stock", source = "quantity")
    Book mapBookDtoToBook(BookDto bookDto);

    @InheritInverseConfiguration
    BookDto mapBookToBookDto(Book book);


}
