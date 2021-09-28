package com.brandjunhoe.bookcatalog.bootCatalog.presentation;

import com.brandjunhoe.bookcatalog.bootCatalog.application.BookCatalogService;
import com.brandjunhoe.bookcatalog.bootCatalog.application.dto.BookCatalogDTO;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalog;
import com.brandjunhoe.bookcatalog.bootCatalog.presentation.dto.ReqBookCatalogRegistDTO;
import com.brandjunhoe.bookcatalog.bootCatalog.presentation.dto.ReqBookCatalogUpdateDTO;
import com.brandjunhoe.bookcatalog.common.page.ReqPageDTO;
import com.brandjunhoe.bookcatalog.common.page.ResPageDTO;
import com.brandjunhoe.bookcatalog.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/book-catalogs")
public class BookCatalogController {

    private final BookCatalogService bookCatalogService;

    public BookCatalogController(BookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @PostMapping
    public ApiResponse bookCatalogService(@RequestBody @Valid ReqBookCatalogRegistDTO registDTO) {

        bookCatalogService.save(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse updateBookCatalog(@PathVariable @Valid @Min(value = 1) Long id,
                                         @RequestBody @Valid ReqBookCatalogUpdateDTO updateDTO) {


        bookCatalogService.update(id, updateDTO);

        return new ApiResponse();
    }

    @GetMapping
    public ApiResponse<ResPageDTO> getAllBookCatalogs(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = bookCatalogService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }

    @GetMapping("/{id}")
    public ApiResponse<BookCatalogDTO> getBookCatalog(@PathVariable @Valid @Min(value = 1) Long id) {

        BookCatalogDTO bookCatalogDTO = bookCatalogService.findOne(id);

        return new ApiResponse(bookCatalogDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteBookCatalog(@PathVariable @Valid @Min(value = 1) Long id) {

        bookCatalogService.delete(id);

        return new ApiResponse();
    }

    @GetMapping
    public ApiResponse<ResPageDTO> getBookByTitle(@RequestParam @Valid @NotBlank String title,
                                                  ReqPageDTO requestPage) {

        ResPageDTO pageDTO = bookCatalogService.findByTitle(title, requestPage.getPageable());

        return new ApiResponse(pageDTO);
    }

    @GetMapping("/top-10")
    public ApiResponse<List<BookCatalogDTO>> loadTop10Books() {

        List<BookCatalogDTO> bookCatalogs = bookCatalogService.loadTop10();

        return new ApiResponse(bookCatalogs);
    }

}
