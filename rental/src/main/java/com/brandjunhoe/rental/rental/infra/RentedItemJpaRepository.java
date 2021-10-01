package com.brandjunhoe.rental.rental.infra;

import com.brandjunhoe.rental.rental.domain.RentedItem;
import com.brandjunhoe.rental.rental.domain.RentedItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/09/28.
 */
public interface RentedItemJpaRepository extends JpaRepository<RentedItem, Long>, RentedItemRepository {

}
