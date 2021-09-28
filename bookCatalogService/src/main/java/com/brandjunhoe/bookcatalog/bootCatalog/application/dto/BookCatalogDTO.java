package com.brandjunhoe.bookcatalog.bootCatalog.application.dto;

import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalog;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.Classification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
@Setter
public class BookCatalogDTO {

    private String id;

    private Long bookId;

    private String title;

    private String description;

    private String author;

    private LocalDate publicationDate;

    private Classification classification;

    private Boolean rented;

    private Long rentCnt;


    public BookCatalogDTO(BookCatalog source) {
        copyProperties(source, this);
    }


}
