package com.brandjunhoe.rental.rental.presentation;

import com.brandjunhoe.rental.common.page.ReqPageDTO;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import com.brandjunhoe.rental.rental.application.OverdueItemService;
import com.brandjunhoe.rental.rental.application.ReturnedItemService;
import com.brandjunhoe.rental.rental.application.dto.OverdueItemDTO;
import com.brandjunhoe.rental.rental.application.dto.RentalDTO;
import com.brandjunhoe.rental.rental.application.dto.ReturnedItemDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqOverdueItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqOverdueItemUpdateDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqReturnedItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqReturnedItemUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/rented-items")
public class OverdueItemController {

    private final OverdueItemService overdueItemService;

    public OverdueItemController(OverdueItemService overdueItemService) {
        this.overdueItemService = overdueItemService;
    }

    @PostMapping
    public ApiResponse createOverdueItem(@RequestBody @Valid ReqOverdueItemRegistDTO registDTO) {

        overdueItemService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse updateOverdueItem(@PathVariable @Valid @Min(value = 1) Long id,
                                          @RequestBody ReqOverdueItemUpdateDTO updateDTO) {

        overdueItemService.update(id, updateDTO);

        return new ApiResponse();
    }


    @GetMapping
    public ApiResponse<ResPageDTO> getAllOverdueItems(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = overdueItemService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);

    }

    @GetMapping("/{id}")
    public ApiResponse<OverdueItemDTO> getOverdueItem(@PathVariable @Valid @Min(value = 1) Long id) {

        OverdueItemDTO overdueItemDTO = overdueItemService.findOne(id);

        return new ApiResponse(overdueItemDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteOverdueItem(@PathVariable @Valid @Min(value = 1) Long id) {

        overdueItemService.delete(id);

        return new ApiResponse();
    }


    @GetMapping("/rental/{rentalId}")
    public ApiResponse<ResPageDTO> loadOverdueByRental(@PathVariable @Valid @Min(value = 1) Long rentalId,
                                                             ReqPageDTO requestPage) {

        ResPageDTO pageDTO = overdueItemService.findReturnedItemsByRental(rentalId, requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }


}
