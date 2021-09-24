package com.brandjunhoe.book.inStockBook.presentation;

import com.brandjunhoe.book.common.page.ReqPageDTO;
import com.brandjunhoe.book.common.page.ResPageDTO;
import com.brandjunhoe.book.common.response.ApiResponse;
import com.brandjunhoe.book.inStockBook.application.InStockBookService;
import com.brandjunhoe.book.inStockBook.application.dto.InStockBookDTO;
import com.brandjunhoe.book.inStockBook.presentation.dto.ReqInStockBookRegistDTO;
import com.brandjunhoe.book.inStockBook.presentation.dto.ReqInStockBookUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/in-stock-books")
public class InStockBookController {

    private final InStockBookService inStockBookService;

    public InStockBookController(InStockBookService inStockBookService) {
        this.inStockBookService = inStockBookService;
    }

    @PostMapping
    public ApiResponse createInStockBook(@RequestBody @Valid ReqInStockBookRegistDTO registDTO) {

        inStockBookService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse updateInStockBook(@PathVariable @Valid @Min(value = 1) Long id,
                                         @RequestBody ReqInStockBookUpdateDTO updateDTO) {


        inStockBookService.update(id, updateDTO);

        return new ApiResponse();
    }

    @GetMapping
    public ApiResponse<ResPageDTO> getAllInStockBooks(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = inStockBookService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }

    @GetMapping("/{id}")
    public ApiResponse<InStockBookDTO> getInStockBook(@PathVariable @Valid @Min(value = 1) Long id) {

        InStockBookDTO inStockBookDTO = inStockBookService.findOne(id);

        return new ApiResponse(inStockBookDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteBook(@PathVariable @Valid @Min(value = 1) Long id) {

        inStockBookService.delete(id);

        return new ApiResponse();
    }

    @GetMapping
    public ApiResponse<ResPageDTO> getInStockBookByTitle(@RequestParam @Valid @NotBlank String title,
                                                         ReqPageDTO requestPage) {

        ResPageDTO pageDTO = inStockBookService.findByTitle(title, requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }


}
