package com.brandjunhoe.rental.common.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResPageDTO<T> {

    private TotalPageDTO total;
    private T content;


}
