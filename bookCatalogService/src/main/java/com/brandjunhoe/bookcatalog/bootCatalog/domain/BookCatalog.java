package com.brandjunhoe.bookcatalog.bootCatalog.domain;

import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookUpdateChanged;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Create by DJH on 2021/09/27.
 */
@Document(collection = "book_catalog")

@Getter
public class BookCatalog {

    @Id
    private String id;

    private Long bookId;

    private String title;

    private String description;

    private String author;

    private LocalDate publicationDate;

    private Classification classification;

    private Boolean rented;

    private Long rentCnt;

    private LocalDateTime deletedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatetedAt;


    @Builder
    public BookCatalog(Long bookId, String title, String description, String author,
                       LocalDate publicationDate, Classification classification, Boolean rented, Long rentCnt) {

        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publicationDate = publicationDate;
        this.classification = classification;
        this.rented = rented;
        this.rentCnt = rentCnt;
    }

    public void update(String title, String description, String author, LocalDate publicationDate,
                       Classification classification, Boolean rented, Long rentCnt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publicationDate = publicationDate;
        this.classification = classification;
        this.rented = rented;
        this.rentCnt = rentCnt;

    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void rentBook() {
        this.rentCnt += 1L;
        this.rented = true;
    }

    public void returnBook() {
        this.rented = false;
    }


}
