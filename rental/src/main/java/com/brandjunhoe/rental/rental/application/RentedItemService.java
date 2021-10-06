package com.brandjunhoe.rental.rental.application;

import com.brandjunhoe.rental.common.exception.DataNotFoundException;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.page.TotalPageDTO;
import com.brandjunhoe.rental.rental.application.dto.RentedItemDTO;
import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.RentalRepository;
import com.brandjunhoe.rental.rental.domain.RentedItem;
import com.brandjunhoe.rental.rental.domain.RentedItemRepository;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalUpdateDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentedItemRegistDTO;
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
public class RentedItemService {

    private final RentalRepository rentalRepository;
    private final RentedItemRepository rentedItemRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RentedItemService(RentalRepository rentalRepository, RentedItemRepository rentedItemRepository, ApplicationEventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.rentedItemRepository = rentedItemRepository;
        this.eventPublisher = eventPublisher;
    }

    public void save(ReqRentedItemRegistDTO registDTO) {
        rentedItemRepository.save(registDTO.toEntity());
    }

    @Transactional(readOnly = true)
    public ResPageDTO findAll(Pageable pageable) {
        Page<RentedItem> page = rentedItemRepository.findAll(pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<RentedItemDTO> rentals = page.getContent()
                .stream().map(RentedItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, rentals);
    }

    @Transactional
    public void update(Long id, ReqRentedItemUpdateDTO updateDTO) {
        RentedItem rentedItem = rentedItemRepository.findById(id).orElseThrow(() -> new DataNotFoundException("rentedItem not content"));
        rentedItem.update(updateDTO.getBookId(), updateDTO.getRentedDate(), updateDTO.getDueDate(), updateDTO.getBookTitle());
    }

    @Transactional(readOnly = true)
    public RentedItemDTO findOne(Long id) {
        return rentedItemRepository.findOne(id).map(RentedItemDTO::new)
                .orElseThrow(() -> new DataNotFoundException("rentedItem not content"));
    }

    public void delete(Long id) {
        rentedItemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RentedItemDTO> findAllForManage() {
        return rentedItemRepository.findAll().stream()
                .map(RentedItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResPageDTO findByTitle(String title, Pageable pageable) {
        Page<RentedItem> page = rentedItemRepository.findByBookTitleContaining(title, pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<RentedItemDTO> rentals = page.getContent()
                .stream().map(RentedItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, rentals);
    }


    @Transactional(readOnly = true)
    public ResPageDTO findRentedItemsByRental(Long rentalId, Pageable pageable) {

        Rental rental = rentalRepository.findByIdAndDeletedAtIsNull(rentalId).orElseThrow(() -> new DataNotFoundException("rental not content"));

        Page<RentedItem> page = rentedItemRepository.findByRental(rental, pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<RentedItemDTO> rentals = page.getContent()
                .stream().map(RentedItemDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, rentals);

    }


}
