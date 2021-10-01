package com.brandjunhoe.rental.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/09/28.
 */
@Entity
@Table(name = "returned_item")
public class ReturnedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("overdueItems")
    private Rental rental;

    @Builder
    public ReturnedItem(Long bookId, String bookTitle, LocalDate returnedDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.returnedDate = returnedDate;
    }

    public void updateBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void updateDueDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public void updateBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void updateRental(Rental rental) {
        this.rental = rental;
    }


}
