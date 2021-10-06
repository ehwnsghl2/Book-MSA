package com.brandjunhoe.rental.rental.application.dto;

import com.brandjunhoe.rental.rental.domain.RentedItem;
import lombok.Getter;

import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/10/6.
 */
@Getter
public class RentedItemDTO {

    private Long id;

    private Long bookId;

    private LocalDate rentedDate;

    private LocalDate dueDate;

    private String bookTitle;

    public RentedItemDTO(RentedItem source){
        copyProperties(source, this);
    }


}
