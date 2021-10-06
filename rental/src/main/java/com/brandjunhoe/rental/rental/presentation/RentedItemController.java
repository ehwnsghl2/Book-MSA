package com.brandjunhoe.rental.rental.presentation;

import com.brandjunhoe.rental.client.dto.BookInfoDTO;
import com.brandjunhoe.rental.client.dto.LatefeeDTO;
import com.brandjunhoe.rental.common.exception.BadRequestException;
import com.brandjunhoe.rental.common.exception.FeignClientException;
import com.brandjunhoe.rental.common.page.ReqPageDTO;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import com.brandjunhoe.rental.rental.application.RentedItemService;
import com.brandjunhoe.rental.rental.application.dto.RentalDTO;
import com.brandjunhoe.rental.rental.application.dto.RentedItemDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/rented-items")
public class RentedItemController {

    private final RentedItemService rentedItemService;

    public RentedItemController(RentedItemService rentedItemService) {
        this.rentedItemService = rentedItemService;
    }

    @PostMapping
    public ApiResponse createRentedItem(@RequestBody @Valid ReqRentedItemRegistDTO registDTO) {

        rentedItemService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse updateRentedItem(@PathVariable @Valid @Min(value = 1) Long id,
                                        @RequestBody ReqRentedItemUpdateDTO updateDTO) {

        rentedItemService.update(id, updateDTO);

        return new ApiResponse();
    }


    @GetMapping
    public ApiResponse<ResPageDTO> getAllRentedItems(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = rentedItemService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);

    }

    @GetMapping("/{id}")
    public ApiResponse<RentedItemDTO> getRentedItem(@PathVariable @Valid @Min(value = 1) Long id) {

        RentedItemDTO rentedItemDTO = rentedItemService.findOne(id);

        return new ApiResponse(rentedItemDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteRentedItem(@PathVariable @Valid @Min(value = 1) Long id) {

        rentedItemService.delete(id);

        return new ApiResponse();
    }


    @GetMapping("/title/{title}")
    public ApiResponse<ResPageDTO> loadRentedItemsByTitle(@RequestParam @Valid @NotBlank String title,
                                                             ReqPageDTO requestPage) {

        ResPageDTO pageDTO = rentedItemService.findByTitle(title, requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }

    @GetMapping("/rental/{rentalId}")
    public ApiResponse<ResPageDTO> loadRentedItemsByRental(@PathVariable @Valid @Min(value = 1) Long rentalId,
                                                              ReqPageDTO requestPage) {

        ResPageDTO pageDTO = rentedItemService.findRentedItemsByRental(rentalId, requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }


}
