package com.brandjunhoe.rental.rental.domain;

import com.jayway.jsonpath.JsonPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Create by DJH on 2021/09/28.
 */
public interface RentedItemRepository {

    RentedItem save(RentedItem toEntity);

    List<RentedItem> findAll();

    Page<RentedItem> findAll(Pageable pageable);

    Page<RentedItem> findByBookTitleContaining(String title, Pageable pageable);

    Page<RentedItem> findByRental(Rental rental, Pageable pageable);

    Optional<RentedItem> findOne(Long id);

    void deleteById(Long id);

    Optional<RentedItem> findById(Long id);
}
