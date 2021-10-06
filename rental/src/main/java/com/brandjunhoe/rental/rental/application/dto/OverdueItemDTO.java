package com.brandjunhoe.rental.rental.application.dto;

import com.brandjunhoe.rental.rental.domain.OverdueItem;
import com.brandjunhoe.rental.rental.domain.ReturnedItem;
import lombok.Getter;

import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Create by DJH on 2021/10/6.
 */
@Getter
public class OverdueItemDTO {

    private Long id;

    private Long bookId;

    private LocalDate dueDate;

    private String bookTitle;

    public OverdueItemDTO(OverdueItem source){
        copyProperties(source, this);
    }


}
