package com.brandjunhoe.book.book.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InStockBooDeleteEvent {

    private Long inStockId;

}
