package com.brandjunhoe.rental.rental.presentation;

import com.brandjunhoe.rental.common.page.ReqPageDTO;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import com.brandjunhoe.rental.rental.application.RentalService;
import com.brandjunhoe.rental.rental.application.dto.RentalDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ApiResponse createRental(@RequestBody @Valid ReqRentalRegistDTO registDTO) {

        rentalService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping
    public ApiResponse updateRental(@PathVariable @Valid @Min(value = 1) Long id,
                                  @RequestBody ReqRentalUpdateDTO updateDTO) {

        rentalService.update(id, updateDTO);

        return new ApiResponse();
    }


    @GetMapping
    public ApiResponse<ResPageDTO> getAllRentals(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = rentalService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);

    }

    @GetMapping("/{id}")
    public ApiResponse<RentalDTO> getRental(@PathVariable @Valid @Min(value = 1) Long id) {

        RentalDTO rentalDTO = rentalService.findOne(id);

        return new ApiResponse(rentalDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteRental(@PathVariable @Valid @Min(value = 1) Long id) {

        rentalService.delete(id);

        return new ApiResponse();
    }


}
