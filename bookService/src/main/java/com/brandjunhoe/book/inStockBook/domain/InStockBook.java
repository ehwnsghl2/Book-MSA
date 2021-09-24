package com.brandjunhoe.book.inStockBook.domain;


import com.brandjunhoe.book.common.doamin.BaseDateEntity;
import lombok.AllArgsConstructor;
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
@Table(name = "in_stock_book")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor
public class InStockBook extends BaseDateEntity {

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
    @Column(name = "source")
    private Source source;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Builder
    public InStockBook(String title, String description, String author,
                       String publisher, Long isbn, Source source) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationDate = LocalDate.now();
        this.source = source;
    }

    public void delete() {
        this.deletedAt = LocalDate.now();
    }

    public void update(String title, String description, String author, String publisher,
                       Long isbn, LocalDate publicationDate, Source source) {

        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.source = source;

    }
}
