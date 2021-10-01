package com.brandjunhoe.rental.rental.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by DJH on 2021/10/01.
 */

@AllArgsConstructor
@Getter
public class BookSavePointsEvent {

    private Long userId;
    private int points;

}
