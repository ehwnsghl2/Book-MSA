package com.brandjunhoe.rental.rental.presentation;

import com.brandjunhoe.rental.common.page.ReqPageDTO;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import com.brandjunhoe.rental.rental.application.RentedItemService;
import com.brandjunhoe.rental.rental.application.ReturnedItemService;
import com.brandjunhoe.rental.rental.application.dto.RentalDTO;
import com.brandjunhoe.rental.rental.application.dto.RentedItemDTO;
import com.brandjunhoe.rental.rental.application.dto.ReturnedItemDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemUpdateDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqReturnedItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqReturnedItemUpdateDTO;
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
public class ReturnedItemController {

    private final ReturnedItemService returnedItemService;

    public ReturnedItemController(ReturnedItemService returnedItemService) {
        this.returnedItemService = returnedItemService;
    }

    @PostMapping
    public ApiResponse createReturnedItem(@RequestBody @Valid ReqReturnedItemRegistDTO registDTO) {

        returnedItemService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse updateReturnedItem(@PathVariable @Valid @Min(value = 1) Long id,
                                          @RequestBody ReqReturnedItemUpdateDTO updateDTO) {

        returnedItemService.update(id, updateDTO);

        return new ApiResponse();
    }


    @GetMapping
    public ApiResponse<ResPageDTO> getAllReturnedItems(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = returnedItemService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);

    }

    @GetMapping("/{id}")
    public ApiResponse<ReturnedItemDTO> getReturnedItem(@PathVariable @Valid @Min(value = 1) Long id) {

        ReturnedItemDTO rentedItemDTO = returnedItemService.findOne(id);

        return new ApiResponse(rentedItemDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteReturnedItem(@PathVariable @Valid @Min(value = 1) Long id) {

        returnedItemService.delete(id);

        return new ApiResponse();
    }


    @GetMapping("/rental/{rentalId}")
    public ApiResponse<ResPageDTO> loadReturnedItemsByRental(@PathVariable @Valid @Min(value = 1) Long rentalId,
                                                             ReqPageDTO requestPage) {

        ResPageDTO pageDTO = returnedItemService.findReturnedItemsByRental(rentalId, requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }


}
