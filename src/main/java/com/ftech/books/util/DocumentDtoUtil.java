package com.ftech.books.util;


import com.ftech.books.document.Book;
import com.ftech.books.dto.BookDto;
import org.springframework.beans.BeanUtils;

public class DocumentDtoUtil {

    public static BookDto toDto(Book book){
        BookDto dto = new BookDto();
        BeanUtils.copyProperties(book, dto);
        return dto;
    }

    public static Book toEntity(BookDto bookDto){
        Book product = new Book();
        BeanUtils.copyProperties(bookDto, product);
        return product;
    }

}
