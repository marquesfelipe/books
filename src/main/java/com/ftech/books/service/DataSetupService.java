package com.ftech.books.service;

import com.ftech.books.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private BookService service;

    @Override
    public void run(String... args) throws Exception {
        BookDto b1 = BookDto.builder().author("Paulo Coelho").name("O diario de um mago").build();
        BookDto b2 = BookDto.builder().author("Paulo Coelho").name("O Alquimista").build();
        BookDto b3 = BookDto.builder().author("Luiz Felipe Ponde").name("Filosofia do cotidiano").build();
        BookDto b4 = BookDto.builder().author("Robert C. Martin").name("Código Limpo").build();

        Flux.just(b1, b2, b3, b4)
                .flatMap(p -> this.service.insertBook(Mono.just(p)))
                .subscribe(System.out::println);

    }
}
