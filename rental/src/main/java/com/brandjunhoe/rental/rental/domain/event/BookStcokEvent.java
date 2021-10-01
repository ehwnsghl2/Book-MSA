package com.brandjunhoe.rental.rental.domain.event;

import com.brandjunhoe.rental.rental.domain.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookStcokEvent {

    private Long bookId;
    private BookStatus bookStatus;

}
