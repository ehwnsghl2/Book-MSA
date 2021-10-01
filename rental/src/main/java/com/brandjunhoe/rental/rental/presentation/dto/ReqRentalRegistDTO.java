package com.brandjunhoe.rental.rental.presentation.dto;

import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.enums.RentalStatus;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.print.Book;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
public class ReqRentalRegistDTO {


    @NotBlank
    private Long userId;

    @NotBlank
    private RentalStatus rentalStatus;

    @Min(value = 1)
    private int lateFee;


    public Rental toEntity() {
        return Rental.builder()
                .userId(userId)
                .rentalStatus(rentalStatus)
                .lateFee(lateFee)
                .build();
    }

}
