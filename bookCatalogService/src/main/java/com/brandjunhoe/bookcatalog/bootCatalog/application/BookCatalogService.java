package com.brandjunhoe.bookcatalog.bootCatalog.application;

import com.brandjunhoe.bookcatalog.bootCatalog.application.dto.BookCatalogDTO;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalog;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalogRepository;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.*;
import com.brandjunhoe.bookcatalog.bootCatalog.presentation.dto.ReqBookCatalogRegistDTO;
import com.brandjunhoe.bookcatalog.bootCatalog.presentation.dto.ReqBookCatalogUpdateDTO;
import com.brandjunhoe.bookcatalog.common.exception.DataNotFoundException;
import com.brandjunhoe.bookcatalog.common.page.ResPageDTO;
import com.brandjunhoe.bookcatalog.common.page.TotalPageDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by DJH on 2021/09/15.
 */
@Service
public class BookCatalogService {

    private final BookCatalogRepository bookCatalogRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookCatalogService(BookCatalogRepository bookCatalogRepository, ApplicationEventPublisher eventPublisher) {
        this.bookCatalogRepository = bookCatalogRepository;
        this.eventPublisher = eventPublisher;
    }


    public void save(ReqBookCatalogRegistDTO registDTO) {
        bookCatalogRepository.save(registDTO.toEntity());
    }


    @Transactional
    @EventListener
    public void update(Long id, ReqBookCatalogUpdateDTO updateDTO) {
        BookCatalog bookCatalog = findById(id);
        bookCatalog.update(updateDTO.getTitle(), updateDTO.getDescription(), updateDTO.getAuthor(), updateDTO.getPublicationDate(),
                updateDTO.getClassification(), updateDTO.getRented(), updateDTO.getRentCnt());
    }

    public ResPageDTO findAll(Pageable pageable) {
        Page<BookCatalog> page = bookCatalogRepository.findAll(pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<BookCatalogDTO> books = page.getContent()
                .stream().map(BookCatalogDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, books);
    }

    public BookCatalogDTO findOne(Long id) {
        return bookCatalogRepository.findById(id).map(BookCatalogDTO::new)
                .orElseThrow(() -> new DataNotFoundException("bookCatalog not content"));
    }

    public void delete(Long id) {
        BookCatalog bookCatalog = findById(id);
        bookCatalog.delete();

        bookCatalogRepository.save(bookCatalog);

    }

    public ResPageDTO findByTitle(String title, Pageable pageable) {
        Page<BookCatalog> page = bookCatalogRepository.findByTitleContaining(title, pageable);
        return resPageDTO(page);
    }


    @Transactional
    @EventListener
    public void registerNewBook(BookCreateChanged bookCreateChanged) {
        BookCatalog bookCatalog = bookCreateChanged.toEntity();
        bookCatalogRepository.save(bookCatalog);
    }


    @Transactional
    @EventListener
    public void deleteBook(BookDeleteChanged bookDeleteChanged) {
        bookCatalogRepository.deleteByBookId(bookDeleteChanged.getBookId());
    }

    @Transactional
    @EventListener
    public void updateBookRentStatus(BookRentBookEvent event) {
        BookCatalog bookCatalog = findByBookId(event.getBookId());
        bookCatalog.rentBook();
    }

    @Transactional
    @EventListener
    public void updateBookReturnStatus(BookReturnBookEvent event) {
        BookCatalog bookCatalog = findByBookId(event.getBookId());
        bookCatalog.returnBook();
    }


   /* public void updateBookInfo(BookUpdateChanged bookChanged) {
        BookCatalog bookCatalog = findByBookId(bookChanged.getBookId());
        bookCatalog.update(bookChanged.getTitle(), bookChanged.getDescription(), bookChanged.getAuthor(), bookChanged.getPublicationDate(),
                bookChanged.getClassification(), bookChanged.getRented(), bookChanged.getRentCnt());
    }*/

    public List<BookCatalogDTO> loadTop10() {
        return bookCatalogRepository.findTop10ByOrderByRentCntDesc()
                .stream().map(BookCatalogDTO::new).collect(Collectors.toList());
    }


    private BookCatalog findById(Long id) {
        return bookCatalogRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new IllegalArgumentException());
    }

    private BookCatalog findByBookId(Long id) {
        return bookCatalogRepository.findByBookIdAndDeletedAtIsNull(id).orElseThrow(() -> new IllegalArgumentException());
    }

    private ResPageDTO resPageDTO(Page<BookCatalog> page) {
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<BookCatalogDTO> books = page.getContent()
                .stream().map(BookCatalogDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, books);
    }


}
