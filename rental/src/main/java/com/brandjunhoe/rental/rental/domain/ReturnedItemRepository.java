package com.brandjunhoe.rental.rental.domain;

import com.jayway.jsonpath.JsonPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Create by DJH on 2021/09/28.
 */
public interface ReturnedItemRepository {

    ReturnedItem save(ReturnedItem toEntity);

    Page<ReturnedItem> findAll(Pageable pageable);

    Optional<ReturnedItem> findOne(Long id);

    void deleteById(Long id);

    Page<ReturnedItem> findByRental(Rental rental, Pageable pageable);

    Optional<ReturnedItem> findById(Long id);
}
