package com.brandjunhoe.book.kafka.sub.event;

import com.brandjunhoe.book.book.domain.BookStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Create by DJH on 2021/09/24.
 */
@Getter
@Setter
public class StockChanged {

    private Long bookId;
    private BookStatus bookStatus;

}


