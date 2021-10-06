package com.brandjunhoe.rental.rental.presentation.dto;

import com.brandjunhoe.rental.rental.domain.RentedItem;
import com.brandjunhoe.rental.rental.domain.ReturnedItem;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/10/6.
 */
@Getter
public class ReqReturnedItemRegistDTO {

    @Min(value = 1)
    private Long bookId;

    @NotNull
    private LocalDate returnedDate;

    @NotBlank
    private String bookTitle;

    @Min(value = 1)
    private Long rentalId;


    public ReturnedItem toEntity() {
        return ReturnedItem.builder()
                .bookId(bookId)
                .returnedDate(returnedDate)
                .bookTitle(bookTitle)
                .rentalId(rentalId)
                .build();
    }

}
