package com.brandjunhoe.user.common.page;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
public class ReqPageDTO {

    private int page = 1;
    private int size = 20;

    public PageRequest getPageable() {
        return PageRequest.of(page - 1, size);
    }

}
