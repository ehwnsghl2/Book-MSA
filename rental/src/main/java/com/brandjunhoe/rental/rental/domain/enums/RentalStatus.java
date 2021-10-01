package com.brandjunhoe.rental.rental.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by DJH on 2021/09/28.
 */
@AllArgsConstructor
@Getter
public enum RentalStatus {

    RENT_AVAILABLE(0,"대출가능","대출가능상태"),
    RENT_UNAVAILABLE(1,"대출불가","대출불가능상태");

    private Integer id;
    private String title;
    private String description;


}
