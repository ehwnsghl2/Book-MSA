package com.brandjunhoe.book.book.infra;

import com.brandjunhoe.book.book.domain.BookRepository;
import com.brandjunhoe.book.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/09/15.
 */
public interface BookJpaRepository extends JpaRepository<Book, Long>, BookRepository {
}
