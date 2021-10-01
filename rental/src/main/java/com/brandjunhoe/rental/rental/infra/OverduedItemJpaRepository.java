package com.brandjunhoe.rental.rental.infra;

import com.brandjunhoe.rental.rental.domain.OverdueItem;
import com.brandjunhoe.rental.rental.domain.OverduedItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/09/28.
 */
public interface OverduedItemJpaRepository extends JpaRepository<OverdueItem, Long>, OverduedItemRepository {

}
