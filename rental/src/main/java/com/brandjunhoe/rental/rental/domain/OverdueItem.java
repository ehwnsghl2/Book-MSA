package com.brandjunhoe.rental.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/09/28.
 */
@Entity
@Table(name = "overdue_item")
@NoArgsConstructor
@Getter
public class OverdueItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("returnedItems")
    private Rental rental;

    @Builder
    public OverdueItem(Long bookId, String bookTitle, LocalDate dueDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.dueDate = dueDate;
    }

    public void updateBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void updateReturnedDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void updateBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void updateRental(Rental rental) {
        this.rental = rental;
    }


}
