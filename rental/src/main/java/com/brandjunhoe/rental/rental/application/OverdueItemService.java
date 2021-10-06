package com.brandjunhoe.rental.rental.application;

import com.brandjunhoe.rental.common.exception.DataNotFoundException;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.page.TotalPageDTO;
import com.brandjunhoe.rental.rental.application.dto.OverdueItemDTO;
import com.brandjunhoe.rental.rental.domain.*;
import com.brandjunhoe.rental.rental.presentation.dto.ReqOverdueItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqOverdueItemUpdateDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemUpdateDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by DJH on 2021/09/28.
 */
@Service
public class OverdueItemService {

    private final RentalRepository rentalRepository;
    private final OverduedItemRepository overduedItemRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OverdueItemService(RentalRepository rentalRepository, OverduedItemRepository overduedItemRepository, ApplicationEventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.overduedItemRepository = overduedItemRepository;
        this.eventPublisher = eventPublisher;
    }

    public void save(ReqOverdueItemRegistDTO registDTO) {
        overduedItemRepository.save(registDTO.toEntity());
    }

    @Transactional
    public void update(Long id, ReqOverdueItemUpdateDTO updateDTO) {
        OverdueItem overdueItem = overduedItemRepository.findById(id).orElseThrow(() -> new DataNotFoundException("rentedItem not content"));
        overdueItem.update(updateDTO.getBookId(), updateDTO.getDueDate(), updateDTO.getBookTitle());
    }

    @Transactional(readOnly = true)
    public ResPageDTO findAll(Pageable pageable) {
        Page<OverdueItem> page = overduedItemRepository.findAll(pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<OverdueItemDTO> rentals = page.getContent()
                .stream().map(OverdueItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, rentals);
    }

    @Transactional(readOnly = true)
    public OverdueItemDTO findOne(Long id) {
        return overduedItemRepository.findOne(id).map(OverdueItemDTO::new)
                .orElseThrow(() -> new DataNotFoundException("overdueItem not content"));
    }

    public void delete(Long id) {
        overduedItemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ResPageDTO findReturnedItemsByRental(Long rentalId, Pageable pageable) {

        Rental rental = rentalRepository.findByIdAndDeletedAtIsNull(rentalId).orElseThrow(() -> new DataNotFoundException("rental not content"));

        Page<OverdueItem> page = overduedItemRepository.findByRental(rental, pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<OverdueItemDTO> overdues = page.getContent()
                .stream().map(OverdueItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, overdues);

    }

}
