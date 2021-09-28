package com.brandjunhoe.book.book.domain.event;

import com.brandjunhoe.book.book.domain.BookChangedType;
import com.brandjunhoe.book.book.domain.Classification;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class BookChanged {

    private Long bookId;

    private String title;

    private String description;

    private String author;

    private String publicationDate;

    private Classification classification;

    private Boolean rented;

    private BookChangedType eventType;

    private Long rentCnt;

}
