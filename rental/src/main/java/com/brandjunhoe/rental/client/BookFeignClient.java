package com.brandjunhoe.rental.client;

import com.brandjunhoe.rental.client.dto.BookInfoDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Create by DJH on 2021/10/05.
 */
@FeignClient(name = "feign-user", url = "http://localhost:8081")
public interface BookFeignClient {

    @GetMapping("/api/v1/books/bookInfo/{bookId}")
    ApiResponse<BookInfoDTO> findBookInfo(@PathVariable("bookId") @Valid @Min(value = 1) Long bookId);

}
