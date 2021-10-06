package com.brandjunhoe.rental.rental.domain;

import com.jayway.jsonpath.JsonPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Create by DJH on 2021/09/28.
 */
public interface OverduedItemRepository {

    OverdueItem save(OverdueItem toEntity);

    Page<OverdueItem> findAll(Pageable pageable);

    Optional<OverdueItem> findOne(Long id);

    void deleteById(Long id);

    Page<OverdueItem> findByRental(Rental rental, Pageable pageable);

    Optional<OverdueItem> findById(Long id);

}
