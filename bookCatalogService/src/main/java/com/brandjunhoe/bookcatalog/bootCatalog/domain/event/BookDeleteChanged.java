package com.brandjunhoe.bookcatalog.bootCatalog.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDeleteChanged {

    private Long bookId;

}
