package com.brandjunhoe.rental.rental.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.print.Book;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

/**
 * Create by DJH on 2021/09/28.
 */
public interface RentalRepository {


    Rental save(Rental toEntity);

    Page<Rental> findAll(Pageable pageable);

    Optional<Rental> findById(Long id);

    Optional<Rental> findByIdAndDeletedAtIsNull(Long id);

    Optional<Rental> findByUserIdDeletedAtIsNull(Long userId);

    Optional<Rental> findByUserIdAndDeletedAtIsNull(Long userId);
}
