package com.brandjunhoe.book.book.presentation;

import com.brandjunhoe.book.book.application.dto.BookDTO;
import com.brandjunhoe.book.book.application.BookService;
import com.brandjunhoe.book.book.presentation.dto.ReqBookRegistDTO;
import com.brandjunhoe.book.book.presentation.dto.ReqBookUpdateDTO;
import com.brandjunhoe.book.common.page.ReqPageDTO;
import com.brandjunhoe.book.common.page.ResPageDTO;
import com.brandjunhoe.book.common.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ApiResponse createBook(@RequestBody @Valid ReqBookRegistDTO registDTO) {

        bookService.createBook(registDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PostMapping("/{inStockId}")
    public ApiResponse registerBook(@PathVariable @Valid @Min(value = 1) Long inStockId,
                                    @RequestBody @Valid ReqBookRegistDTO registDTO) {

        bookService.registerNewBook(registDTO, inStockId);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping
    public ApiResponse updateBook(@PathVariable @Valid @Min(value = 1) Long id,
                                  @RequestBody ReqBookUpdateDTO updateDTO) {

        bookService.updateBook(id, updateDTO);

        return new ApiResponse();
    }


    @GetMapping
    public ApiResponse<ResPageDTO> getAllBooks(ReqPageDTO requestPage) {

        ResPageDTO pageDTO = bookService.findAll(requestPage.getPageable());

        return new ApiResponse(pageDTO);

    }

    @GetMapping("/{id}")
    public ApiResponse<BookDTO> getBook(@PathVariable @Valid @Min(value = 1) Long id) {

        BookDTO bookDTO = bookService.findOne(id);

        return new ApiResponse(bookDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteBook(@PathVariable @Valid @Min(value = 1) Long id) {

        bookService.delete(id);

        return new ApiResponse();
    }

    @GetMapping("/books/bookInfo/{bookId}")
    public ApiResponse<BookDTO> findBookInfo(@PathVariable @Valid @Min(value = 1) Long bookId){

        BookDTO bookDTO = bookService.findOne(bookId);

        return new ApiResponse(bookDTO);
    }

}
