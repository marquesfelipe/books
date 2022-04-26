package com.ftech.books.document;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class Book {

        @Id
        private String id;
        private String name;
        private String author;
}
