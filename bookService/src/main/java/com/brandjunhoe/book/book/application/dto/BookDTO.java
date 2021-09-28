package com.brandjunhoe.book.book.application.dto;

import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.BookStatus;
import com.brandjunhoe.book.book.domain.Classification;
import com.brandjunhoe.book.book.domain.Location;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
@Setter
public class BookDTO {

    private Long id;

    private String title;

    private String description;

    private String author;

    private String publisher;

    private Long isbn;

    private Classification classification;

    private BookStatus bookStatus;

    private Location location;

    public BookDTO(Book source) {
        copyProperties(source, this);
    }

}
