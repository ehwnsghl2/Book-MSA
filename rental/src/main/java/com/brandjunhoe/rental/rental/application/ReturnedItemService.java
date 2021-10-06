package com.brandjunhoe.rental.rental.application;

import com.brandjunhoe.rental.common.exception.DataNotFoundException;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.page.TotalPageDTO;
import com.brandjunhoe.rental.rental.application.dto.ReturnedItemDTO;
import com.brandjunhoe.rental.rental.domain.*;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemUpdateDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqReturnedItemRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqReturnedItemUpdateDTO;
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
public class ReturnedItemService {

    private final RentalRepository rentalRepository;
    private final ReturnedItemRepository returnedItemRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReturnedItemService(RentalRepository rentalRepository, ReturnedItemRepository returnedItemRepository, ApplicationEventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.returnedItemRepository = returnedItemRepository;
        this.eventPublisher = eventPublisher;
    }

    public void save(ReqReturnedItemRegistDTO registDTO) {
        returnedItemRepository.save(registDTO.toEntity());
    }

    @Transactional(readOnly = true)
    public ResPageDTO findAll(Pageable pageable) {
        Page<ReturnedItem> page = returnedItemRepository.findAll(pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<ReturnedItemDTO> rentals = page.getContent()
                .stream().map(ReturnedItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, rentals);
    }

    @Transactional
    public void update(Long id, ReqReturnedItemUpdateDTO updateDTO) {
        ReturnedItem returnedItem = returnedItemRepository.findById(id).orElseThrow(() -> new DataNotFoundException("returnedItem not content"));
        returnedItem.update(updateDTO.getBookId(), updateDTO.getReturnedDate(), updateDTO.getBookTitle());
    }

    @Transactional(readOnly = true)
    public ReturnedItemDTO findOne(Long id) {
        return returnedItemRepository.findOne(id).map(ReturnedItemDTO::new)
                .orElseThrow(() -> new DataNotFoundException("returnedItem not content"));
    }

    public void delete(Long id) {
        returnedItemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ResPageDTO findReturnedItemsByRental(Long rentalId, Pageable pageable) {

        Rental rental = rentalRepository.findByIdAndDeletedAtIsNull(rentalId).orElseThrow(() -> new DataNotFoundException("rental not content"));

        Page<ReturnedItem> page = returnedItemRepository.findByRental(rental, pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<ReturnedItemDTO> returneds = page.getContent()
                .stream().map(ReturnedItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, returneds);

    }

}
