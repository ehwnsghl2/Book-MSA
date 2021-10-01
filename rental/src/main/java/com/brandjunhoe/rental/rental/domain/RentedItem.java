package com.brandjunhoe.rental.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/09/28.
 */
@Entity
@Table(name = "rented_item")
@NoArgsConstructor
@Getter
public class RentedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "rented_date")
    private LocalDate rentedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("rentedItems")
    private Rental rental;

    @Builder
    public RentedItem(Long bookId, String bookTitle, LocalDate rentedDate) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.rentedDate = rentedDate;
        this.dueDate = rentedDate.plusWeeks(2);
    }

    public void updateBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void updateRentedDate(LocalDate rentedDate) {
        this.rentedDate = rentedDate;
    }

    public void updateBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void updateDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

}
