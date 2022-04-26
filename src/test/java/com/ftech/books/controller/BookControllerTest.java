package com.ftech.books.controller;

import com.ftech.books.dto.BookDto;
import com.ftech.books.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@WebFluxTest
public class BookControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private BookService bookService;


    @Test
    public void getAll() {
        Mockito
                .when(this.bookService.getAll())
                .thenReturn(Flux.just(new BookDto("filosofia", "varios"), new BookDto("matematica", "varios")));


        this.client
                .get()
                .uri("/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].name").isEqualTo("filosofia")
                .jsonPath("$.[0].author").isEqualTo("varios")
                .jsonPath("$.[1].name").isEqualTo("matematica")
                .jsonPath("$.[1].author").isEqualTo("varios");
    }


    @Test
    public void getById() {
        BookDto data = BookDto.builder().author("Paulo Coelho").name("Diario de um mago").build();

        Mockito
                .when(this.bookService.getBookById("1"))
                .thenReturn(Mono.just(data));

        this.client
                .get()
                .uri("/book/" + "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo(data.getName())
                .jsonPath("$.author").isEqualTo(data.getAuthor());
    }

    @Test
    public void insert() {
        BookDto data = BookDto.builder().author("Paulo Coelho").name("Diario de um mago").build();
        Mockito
                .when(this.bookService.insertBook(Mockito.any()))
                .thenReturn(Mono.just(data));
        MediaType jsonUtf8 = MediaType.APPLICATION_JSON;
        this
                .client
                .post()
                .uri("/book")
                .contentType(jsonUtf8)
                .body(Mono.just(data), BookDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(jsonUtf8);
    }


    @Test
    public void update() {
        BookDto data = BookDto.builder().author("Paulo Coelho").name("Diario de um mago").build();

        Mockito
                .when(this.bookService.updateBook(Mockito.anyString(),Mockito.any()))
                .thenReturn(Mono.just(data));

        this
                .client
                .put()
                .uri("/book/" + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(data), BookDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void delete() {

        Mockito
                .when(this.bookService.deleteBook(Mockito.any()))
                .thenReturn(Mono.empty());
        this
                .client
                .delete()
                .uri("/book/" + "1")
                .exchange()
                .expectStatus().isOk();
    }



}
