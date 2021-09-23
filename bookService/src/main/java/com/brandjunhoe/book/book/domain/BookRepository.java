package com.brandjunhoe.book.book.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Create by DJH on 2021/09/15.
 */
public interface BookRepository {

    Book save(Book book);

    Page<Book> findAll(Pageable pageable);

    Optional<Book> findById(Long id);

}
