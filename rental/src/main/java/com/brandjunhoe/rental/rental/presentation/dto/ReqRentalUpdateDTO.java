package com.brandjunhoe.rental.rental.presentation.dto;

import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.enums.RentalStatus;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
public class ReqRentalUpdateDTO {

    @NotBlank
    private Long userId;

    @NotBlank
    private RentalStatus rentalStatus;

    @Min(value = 1)
    private int lateFee;


}
