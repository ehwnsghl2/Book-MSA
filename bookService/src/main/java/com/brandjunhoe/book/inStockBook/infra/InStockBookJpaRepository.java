package com.brandjunhoe.book.inStockBook.infra;

import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.BookRepository;
import com.brandjunhoe.book.inStockBook.domain.InStockBook;
import com.brandjunhoe.book.inStockBook.domain.InStockBookRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/09/15.
 */
public interface InStockBookJpaRepository extends JpaRepository<InStockBook, Long>, InStockBookRepository {
}
