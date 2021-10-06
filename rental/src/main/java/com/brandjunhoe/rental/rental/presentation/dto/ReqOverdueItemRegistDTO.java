package com.brandjunhoe.rental.rental.presentation.dto;

import com.brandjunhoe.rental.rental.domain.OverdueItem;
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
public class ReqOverdueItemRegistDTO {

    @Min(value = 1)
    private Long bookId;

    @NotNull
    private LocalDate dueDate;

    @NotBlank
    private String bookTitle;

    @Min(value = 1)
    private Long rentalId;

    public OverdueItem toEntity() {
        return OverdueItem.builder()
                .bookId(bookId)
                .dueDate(dueDate)
                .bookTitle(bookTitle)
                .build();
    }

}
