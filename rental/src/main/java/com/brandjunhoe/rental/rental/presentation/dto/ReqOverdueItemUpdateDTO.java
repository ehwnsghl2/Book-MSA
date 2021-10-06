package com.brandjunhoe.rental.rental.presentation.dto;

import com.brandjunhoe.rental.rental.domain.OverdueItem;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/10/6.
 */
@Getter
public class ReqOverdueItemUpdateDTO {

    @Min(value = 1)
    private Long bookId;

    @NotNull
    private LocalDate dueDate;

    @NotBlank
    private String bookTitle;

    @Min(value = 1)
    private Long rentalId;

}
