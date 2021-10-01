package com.brandjunhoe.rental.rental.application.dto;

import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.enums.RentalStatus;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
public class RentalDTO {

    private Long id;

    private Long userId;

    private RentalStatus rentalStatus;

    private int lateFee;


    public RentalDTO(Rental source){
        copyProperties(source, this);
    }

}
