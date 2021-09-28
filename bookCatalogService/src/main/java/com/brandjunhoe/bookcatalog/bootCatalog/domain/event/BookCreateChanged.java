package com.brandjunhoe.bookcatalog.bootCatalog.domain.event;

import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalog;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.Classification;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class BookCreateChanged {

    private Long bookId;

    private String title;

    private String description;

    private String author;

    private String publicationDate;

    private Classification classification;

    private Boolean rented;

    private Long rentCnt;

    public BookCatalog toEntity() {
        return BookCatalog.builder()
                .bookId(bookId)
                .title(title)
                .description(description)
                .author(author)
                .publicationDate(LocalDate.parse(publicationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .classification(classification)
                .rented(rented)
                .rentCnt(rentCnt)
                .build();
    }

}

