package com.ftech.books.service;

import com.ftech.books.dto.BookDto;
import com.ftech.books.repository.BookRepository;
import com.ftech.books.util.DocumentDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repository;

    @Override
    public Flux<BookDto> getAll() {
        return this.repository.findAll()
                .map(DocumentDtoUtil::toDto);
    }

    @Override
    public Mono<BookDto> getBookById(String id) {
        return this.repository.findById(id)
                .map(DocumentDtoUtil::toDto);
    }

    @Override
    public Mono<BookDto> insertBook(Mono<BookDto> bookDtoMono) {
        return bookDtoMono
                .map(DocumentDtoUtil::toEntity)
                .flatMap(this.repository::insert)
                .map(DocumentDtoUtil::toDto);
    }

    @Override
    public Mono<BookDto> updateBook(String id, Mono<BookDto> bookDtoMono) {
        return this.repository.findById(id)
                .flatMap(p -> bookDtoMono
                        .map(DocumentDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.repository::save)
                .map(DocumentDtoUtil::toDto);
    }

    @Override
    public Mono<Void> deleteBook(String id) {
        return this.repository.deleteById(id);
    }
}
