package com.brandjunhoe.book.book.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class BookDeleteChanged {

    private Long bookId;

}
