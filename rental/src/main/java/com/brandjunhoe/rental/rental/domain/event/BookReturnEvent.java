package com.brandjunhoe.rental.rental.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookReturnEvent {

    private Long bookId;

}
