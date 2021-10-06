package com.brandjunhoe.rental.rental.presentation.dto;

import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.RentedItem;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/10/6.
 */
@Getter
public class ReqRentedItemRegistDTO {

    @Min(value = 1)
    private Long bookId;

    @NotNull
    private LocalDate rentedDate;

    @NotNull
    private LocalDate dueDate;

    @NotBlank
    private String bookTitle;

    @Min(value = 1)
    private Long rentalId;

    public RentedItem toEntity() {
        return RentedItem.builder()
                .bookId(bookId)
                .rentedDate(rentedDate)
                .dueDate(dueDate)
                .bookTitle(bookTitle)
                .rentalId(rentalId)
                .build();
    }

}
