package com.brandjunhoe.book.book.presentation.dto;

import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.BookStatus;
import com.brandjunhoe.book.book.domain.Classification;
import com.brandjunhoe.book.book.domain.Location;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
public class ReqBookRegistDTO {

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
    private Classification classification;

    @NotNull
    private BookStatus bookStatus;

    @NotNull
    private Location location;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .description(description)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .classification(classification)
                .bookStatus(bookStatus)
                .location(location)
                .build();
    }

}
