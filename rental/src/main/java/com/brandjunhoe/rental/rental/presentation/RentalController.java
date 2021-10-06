package com.brandjunhoe.rental.rental.presentation;

import com.brandjunhoe.rental.client.BookFeignClient;
import com.brandjunhoe.rental.client.UserFeignClient;
import com.brandjunhoe.rental.client.dto.BookInfoDTO;
import com.brandjunhoe.rental.client.dto.LatefeeDTO;
import com.brandjunhoe.rental.common.exception.BadRequestException;
import com.brandjunhoe.rental.common.exception.FeignClientException;
import com.brandjunhoe.rental.common.page.ReqPageDTO;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.response.ApiResponse;
import com.brandjunhoe.rental.rental.application.RentalService;
import com.brandjunhoe.rental.rental.application.dto.RentalDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalUpdateDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BookFeignClient bookFeignClient;
    private final UserFeignClient userFeignClient;

    public RentalController(RentalService rentalService, BookFeignClient bookFeignClient, UserFeignClient userFeignClient) {
        this.rentalService = rentalService;
        this.bookFeignClient = bookFeignClient;
        this.userFeignClient = userFeignClient;
    }

    @PostMapping
    public ApiResponse createRental(@RequestBody @Valid ReqRentalRegistDTO registDTO) {

        rentalService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
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


    /**
     * 도서 대출하기
     */
    @PostMapping("/{userId}/rented-item/{bookId}")
    public ApiResponse rentBooks(@PathVariable @Valid @Min(value = 1) Long userId,
                                 @PathVariable @Valid @Min(value = 1) Long bookId) {

        // feign client - 책 정보 가져오기
        ApiResponse<BookInfoDTO> result = bookFeignClient.findBookInfo(bookId);

        if (result.getStatus() != 200) throw new BadRequestException("not found user feign client");

        BookInfoDTO bookInfoDTO = result.getData();

        rentalService.rentBook(userId, bookInfoDTO.getId(), bookInfoDTO.getTitle());


        return new ApiResponse();
    }

    /**
     * 도서 반납하기
     */
    @DeleteMapping("/{userid}/rented-item/{book}")
    public ApiResponse returnBooks(@PathVariable @Valid @Min(value = 1) Long userId,
                                   @PathVariable @Valid @Min(value = 1) Long bookId) {

        rentalService.returnBook(userId, bookId);

        return new ApiResponse();
    }

    /**
     * 도서 연체 처리하기
     */
    @PostMapping("/{rentalId}/overdue-item/{bookId}")
    public ApiResponse beOverDue(@PathVariable @Valid @Min(value = 1) Long rentalId,
                                 @PathVariable @Valid @Min(value = 1) Long bookId) {

        rentalService.beOverdueBook(rentalId, bookId);

        return new ApiResponse();
    }

    /**
     * 연체도서 반납하기
     */
    @DeleteMapping("/{userId}/overdue-item/{bookId}")
    public ApiResponse returnOverdueBook(@PathVariable @Valid @Min(value = 1) Long userId,
                                         @PathVariable @Valid @Min(value = 1) Long bookId) {

        rentalService.returnOverdueBook(userId, bookId);

        return new ApiResponse();
    }

    /**
     * 대출불가 해제하기
     */
    @PutMapping("/release-overdue/user/{userId}")
    public ApiResponse releaseOverdue(@PathVariable @Valid @Min(value = 1) Long userId) {

        int latefee = rentalService.findLatefee(userId);

        try {
            userFeignClient.usePoint(new LatefeeDTO(userId, latefee));
        } catch (FeignClientException e) {
            if (!Integer.valueOf(HttpStatus.NOT_FOUND.value()).equals(e.getStatus()))
                throw e;

        }

        rentalService.releaseOverdue(userId);

        return new ApiResponse();
    }


    @GetMapping("/loadInfo/{userId}")
    public ApiResponse<RentalDTO> loadRentalInfo(@PathVariable @Valid @Min(value = 1) Long userId) {

        RentalDTO rentalDTO = rentalService.findRentalByUser(userId);

        return new ApiResponse(rentalDTO);
    }

}
