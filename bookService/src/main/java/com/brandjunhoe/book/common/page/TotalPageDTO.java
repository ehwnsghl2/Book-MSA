package com.brandjunhoe.book.common.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalPageDTO {

    private int number;
    private int totalPages;
    private Long totalElements;

    public int getNumber() {
        return number + 1;
    }
}
