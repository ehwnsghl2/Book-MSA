package com.brandjunhoe.book.inStockBook.domain;

import com.brandjunhoe.book.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Create by DJH on 2021/09/15.
 */
public interface InStockBookRepository {

    Page<InStockBook> findAll(Pageable pageable);

    InStockBook save(InStockBook inStockBook);

    Optional<InStockBook> findById(Long id);

    Page<InStockBook> findByTitleContaining(String title, Pageable pageable);

}
