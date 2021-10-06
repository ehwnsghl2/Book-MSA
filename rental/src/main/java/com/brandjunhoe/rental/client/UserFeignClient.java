package com.brandjunhoe.rental.client;

import com.brandjunhoe.rental.client.dto.LatefeeDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Create by DJH on 2021/10/06.
 */

@FeignClient(name = "userService", url = "http://localhost:8080")
public interface UserFeignClient {

    @PutMapping("/api/users/latefee")
    ApiResponse usePoint(@RequestBody LatefeeDTO latefeeDTO);


}
