package com.brandjunhoe.book.inStockBook.application.dto;

import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.BookStatus;
import com.brandjunhoe.book.book.domain.Classification;
import com.brandjunhoe.book.book.domain.Location;
import com.brandjunhoe.book.inStockBook.domain.InStockBook;
import com.brandjunhoe.book.inStockBook.domain.Source;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
@Setter
public class InStockBookDTO {

    private String title;

    private String description;

    private String author;

    private String publisher;

    private Long isbn;

    private LocalDate publicationDate;

    private Source source;

    public InStockBookDTO(InStockBook source) {
        copyProperties(source, this);
    }


}
