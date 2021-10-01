package com.brandjunhoe.rental.rental.application;

import com.brandjunhoe.rental.common.exception.DataNotFoundException;
import com.brandjunhoe.rental.common.page.ResPageDTO;
import com.brandjunhoe.rental.common.page.TotalPageDTO;
import com.brandjunhoe.rental.kafka.sub.event.CreateRentalEvent;
import com.brandjunhoe.rental.rental.application.dto.RentalDTO;
import com.brandjunhoe.rental.rental.domain.Rental;
import com.brandjunhoe.rental.rental.domain.RentalRepository;
import com.brandjunhoe.rental.rental.domain.enums.BookStatus;
import com.brandjunhoe.rental.rental.domain.event.BookRentEvent;
import com.brandjunhoe.rental.rental.domain.event.BookReturnEvent;
import com.brandjunhoe.rental.rental.domain.event.BookSavePointsEvent;
import com.brandjunhoe.rental.rental.domain.event.BookStcokEvent;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalRegistDTO;
import com.brandjunhoe.rental.rental.presentation.dto.ReqRentalUpdateDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
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
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ApplicationEventPublisher eventPublisher;

    private int pointPerBooks = 30;

    public RentalService(RentalRepository rentalRepository, ApplicationEventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.eventPublisher = eventPublisher;
    }

    public void save(ReqRentalRegistDTO registDTO) {
        rentalRepository.save(registDTO.toEntity());
    }

    @Transactional
    public void update(Long id, ReqRentalUpdateDTO updateDTO) {
        Rental rental = findById(id);
        rental.update(updateDTO.getUserId(), updateDTO.getRentalStatus(), updateDTO.getLateFee());
    }

    @Transactional(readOnly = true)
    public ResPageDTO findAll(Pageable pageable) {
        Page<Rental> page = rentalRepository.findAll(pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<RentalDTO> rentals = page.getContent()
                .stream().map(RentalDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, rentals);
    }

    @Transactional(readOnly = true)
    public RentalDTO findOne(Long id) {
        return rentalRepository.findByIdAndDeletedAtIsNull(id).map(RentalDTO::new)
                .orElseThrow(() -> new DataNotFoundException("rental not content"));
    }


    @Transactional
    public void delete(Long id) {
        Rental rental = findById(id);
        rental.delete();
    }

    @EventListener
    public void createRental(CreateRentalEvent userIdCreated) {
        rentalRepository.save(new Rental(userIdCreated.getUserId()));
    }

    /**
     * 도서 대출하기
     *
     * @param userId
     * @param bookId
     * @param bookTitle
     * @return
     */
    @Transactional
    public void rentBook(Long userId, Long bookId, String bookTitle) {
        Rental rental = rentalRepository.findByUserIdDeletedAtIsNull(userId)
                .orElseThrow(() -> new DataNotFoundException("rental not content"));

        rental.checkRentalAvailable();
        rental.rentBook(bookId, bookTitle);


        // send to book service
        updateBookStatus(bookId, BookStatus.AVAILABLE);
        // send to book catalog service
        updateBookCatalogRentStatus(bookId);
        // send to user service
        savePoints(userId, pointPerBooks);

    }

    public RentalDTO findRentalByUser(Long userId) {
        return rentalRepository.findByUserIdAndDeletedAtIsNull(userId).map(RentalDTO::new)
                .orElseThrow(() -> new DataNotFoundException("user rental not content"));
    }


    @Transactional
    public Rental returnBook(Long userId, Long bookId) {

        Rental rental = findByUserId(userId);
        rental.returnBook(bookId);

        // send to book service
        updateBookStatus(bookId, BookStatus.AVAILABLE);
        // send to book catalog service
        updateBookCatalogReturnStatus(bookId);

        return rental;
    }

    /**
     * 연체처리
     *
     * @param rentalId
     * @param bookId
     * @return
     */
    @Transactional
    public Long beOverdueBook(Long rentalId, Long bookId) {
        Rental rental = findById(rentalId);
        rental.overdueBook(bookId);
        rental.makeRentUnable();
        return bookId;
    }

    /**
     * 연체된 책 반납하기
     *
     * @param userid
     * @param book
     * @return
     */

    @Transactional
    public void returnOverdueBook(Long userid, Long bookId) {

        Rental rental = findByUserId(userid);
        rental.returnOverdueBook(bookId);

        // send to book service
        updateBookStatus(bookId, BookStatus.AVAILABLE);
        // send to book catalog service
        updateBookCatalogReturnStatus(bookId);

    }


    /**
     * @param userId 대출불가 해제하기
     */
    @Transactional
    public void releaseOverdue(Long userId) {
        Rental rental = rentalRepository.findByUserIdAndDeletedAtIsNull(userId).get();
        rental.releaseOverdue();
    }


    public int findLatefee(Long userId) {
        return findByUserId(userId).getLateFee();
    }


    private Rental findById(Long id) {
        return rentalRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new IllegalArgumentException());
    }

    private Rental findByUserId(Long userId) {
        return rentalRepository.findByUserIdAndDeletedAtIsNull(userId).orElseThrow(() -> new DataNotFoundException("user rental not content"));
    }


    private void updateBookStatus(Long bookId, BookStatus bookStatus) {
        eventPublisher.publishEvent(new BookStcokEvent(bookId, bookStatus));
    }

    private void updateBookCatalogRentStatus(Long bookId) {
        eventPublisher.publishEvent(new BookRentEvent(bookId));
    }

    private void updateBookCatalogReturnStatus(Long bookId) {
        eventPublisher.publishEvent(new BookReturnEvent(bookId));
    }

    private void savePoints(Long userId, int pointPerBooks) {
        eventPublisher.publishEvent(new BookSavePointsEvent(userId, pointPerBooks));
    }


}
