package com.brandjunhoe.book.inStockBook.presentation.dto;

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
public class ReqInStockBookUpdateDTO {

    private String title;

    private String description;

    private String author;

    private String publisher;

    private Long isbn;

    private LocalDate publicationDate;

    private Source source;

}
