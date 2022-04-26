package com.ftech.books.service;

import com.ftech.books.dto.BookDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

   Flux<BookDto> getAll();
   Mono<BookDto> getBookById(String id);
   Mono<BookDto> insertBook(Mono<BookDto> bookDtoMono);
   Mono<BookDto> updateBook(String id, Mono<BookDto> bookDtoMono);
   Mono<Void> deleteBook(String id);
}
