package com.brandjunhoe.rental.rental.infra;

import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.RentalRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/09/28.
 */
public interface RentalJpaRepository extends JpaRepository<Rental, Long>, RentalRepository {

}
