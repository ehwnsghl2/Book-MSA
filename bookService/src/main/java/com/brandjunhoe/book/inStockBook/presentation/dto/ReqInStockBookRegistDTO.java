package com.brandjunhoe.book.inStockBook.presentation.dto;

import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.BookStatus;
import com.brandjunhoe.book.book.domain.Classification;
import com.brandjunhoe.book.book.domain.Location;
import com.brandjunhoe.book.inStockBook.domain.InStockBook;
import com.brandjunhoe.book.inStockBook.domain.Source;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
public class ReqInStockBookRegistDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String author;

    @NotBlank
    private String publisher;

    @Min(value = 1)
    private Long isbn;

    @NotNull
    private LocalDate publicationDate;

    @NotNull
    private Source source;

    public InStockBook toEntity() {
        return InStockBook.builder()
                .title(title)
                .description(description)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .source(source)
                .build();
    }

}
