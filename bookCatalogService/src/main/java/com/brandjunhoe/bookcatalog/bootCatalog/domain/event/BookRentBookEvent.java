package com.brandjunhoe.bookcatalog.bootCatalog.domain.event;

import com.brandjunhoe.bookcatalog.bootCatalog.domain.Classification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookRentBookEvent {

    private Long bookId;

    private String title;

    private String description;

    private String author;

    private String publicationDate;

    private Classification classification;

    private Boolean rented;

    private Long rentCnt;

}
