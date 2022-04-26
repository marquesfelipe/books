package com.ftech.books.controller;


import com.ftech.books.dto.BookDto;
import com.ftech.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    public Flux<BookDto> all(){
        return this.service.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable String id){
        return this.service.getBookById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<BookDto> insertBook(@RequestBody Mono<BookDto> bookDto){
        return this.service.insertBook(bookDto);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<BookDto>> updateBook(@PathVariable String id, @RequestBody Mono<BookDto> bookDto){
        return this.service.updateBook(id, bookDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteBook(@PathVariable String id){
        return this.service.deleteBook(id);
    }
}
