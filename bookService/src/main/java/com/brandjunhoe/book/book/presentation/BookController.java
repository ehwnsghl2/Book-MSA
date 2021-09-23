package com.brandjunhoe.book.book.presentation;

import com.brandjunhoe.book.book.application.dto.BookDTO;
import com.brandjunhoe.book.book.application.BookService;
import com.brandjunhoe.book.book.presentation.dto.ReqBookRegistDTO;
import com.brandjunhoe.book.book.presentation.dto.ReqBookUpdateDTO;
import com.brandjunhoe.book.common.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URISyntaxException;
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
    public ApiResponse createBook(@RequestBody @Valid BookDTO bookDTO) {

        bookService.save(bookDTO);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PostMapping("/{inStockId}")
    public ApiResponse registerBook(@PathVariable @Valid @Min(value = 1) Long inStockId,
                                    @RequestBody @Valid ReqBookRegistDTO registDTO) {

        bookService.registerNewBook(registDTO, inStockId);

        return new ApiResponse(HttpStatus.CREATED);
    }

    @PutMapping("/books")
    public ApiResponse updateBook(@PathVariable @Valid @Min(value = 1) Long id,
                                  @RequestBody ReqBookUpdateDTO updateDTO) {

        bookService.updateBook(id, updateDTO);

        return new ApiResponse();
    }

}
