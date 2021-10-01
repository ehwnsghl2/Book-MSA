package com.brandjunhoe.rental.rental.infra;

import com.brandjunhoe.rental.rental.domain.ReturnedItem;
import com.brandjunhoe.rental.rental.domain.ReturnedItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/09/28.
 */
public interface ReturnedItemJpaRepository extends JpaRepository<ReturnedItem, Long>, ReturnedItemRepository {

}
