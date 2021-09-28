package com.brandjunhoe.bookcatalog.bootCatalog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.print.Book;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

/**
 * Create by DJH on 2021/09/27.
 */
public interface BookCatalogRepository {

    BookCatalog save(BookCatalog bookCatalog);

    Page<BookCatalog> findAll(Pageable pageable);

    Optional<BookCatalog> findById(Long id);

    Optional<BookCatalog> findByIdAndDeletedAtIsNull(Long id);

    Page<BookCatalog> findByTitleContaining(String title, Pageable pageable);

    void deleteByBookId(Long bookId);

    Optional<BookCatalog> findByBookIdAndDeletedAtIsNull(Long id);

    List<BookCatalog> findTop10ByOrderByRentCntDesc();
}
