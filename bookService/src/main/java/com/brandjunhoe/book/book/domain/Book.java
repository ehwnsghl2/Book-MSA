package com.brandjunhoe.book.book.domain;

import com.brandjunhoe.book.book.application.dto.BookDTO;
import com.brandjunhoe.book.common.doamin.BaseDateEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/09/15.
 */
@Entity
@Table(name = "book")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor
public class Book extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "isbn")
    private Long isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification")
    private Classification classification;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Builder
    public Book(String title, String description, String author, String publisher, Long isbn,
                Classification classification, BookStatus bookStatus, Location location) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.classification = classification;
        this.publicationDate = LocalDate.now();
        this.bookStatus = bookStatus;
        this.location = location;
    }


    public void update(String title, String description, String author, String publisher,
                       Long isbn, Classification classification, BookStatus bookStatus, Location location) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.classification = classification;
        this.bookStatus = bookStatus;
        this.location = location;
    }

    public void delete() {
        this.deletedAt = LocalDate.now();
    }

    public void bookStatusChanged(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

}
