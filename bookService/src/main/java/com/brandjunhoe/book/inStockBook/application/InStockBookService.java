package com.brandjunhoe.book.inStockBook.application;

import com.brandjunhoe.book.book.application.dto.BookDTO;
import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.event.InStockBooDeleteEvent;
import com.brandjunhoe.book.book.presentation.dto.ReqBookUpdateDTO;
import com.brandjunhoe.book.common.exception.DataNotFoundException;
import com.brandjunhoe.book.common.page.ResPageDTO;
import com.brandjunhoe.book.common.page.TotalPageDTO;
import com.brandjunhoe.book.inStockBook.application.dto.InStockBookDTO;
import com.brandjunhoe.book.inStockBook.domain.InStockBook;
import com.brandjunhoe.book.inStockBook.domain.InStockBookRepository;
import com.brandjunhoe.book.inStockBook.presentation.dto.ReqInStockBookRegistDTO;
import com.brandjunhoe.book.inStockBook.presentation.dto.ReqInStockBookUpdateDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by DJH on 2021/09/15.
 */
@Service
public class InStockBookService {

    private final InStockBookRepository inStockBookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public InStockBookService(InStockBookRepository inStockBookRepository, ApplicationEventPublisher eventPublisher) {
        this.inStockBookRepository = inStockBookRepository;
        this.eventPublisher = eventPublisher;
    }

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void save(ReqInStockBookRegistDTO inStockBookDTO) {
        inStockBookRepository.save(inStockBookDTO.toEntity());
    }

    @Transactional
    public void update(Long id, ReqInStockBookUpdateDTO updateDTO) {

        InStockBook updatedBook = findById(id);

        updatedBook.update(updateDTO.getTitle(), updateDTO.getDescription(), updateDTO.getAuthor(),
                updateDTO.getPublisher(), updateDTO.getIsbn(), updateDTO.getPublicationDate(),
                updateDTO.getSource());


    }

    @Transactional(readOnly = true)
    public ResPageDTO findAll(Pageable pageable) {
        Page<InStockBook> page = inStockBookRepository.findAll(pageable);
        return resPageDTO(page);
    }

    @Transactional(readOnly = true)
    public InStockBookDTO findOne(Long id) {
        return inStockBookRepository.findById(id).map(InStockBookDTO::new)
                .orElseThrow(() -> new DataNotFoundException("in stock book not content"));
    }

    @EventListener
    public void inStockBooDeleteEvent(InStockBooDeleteEvent event) {
        InStockBook inStockBook = findById(event.getInStockId());
        inStockBook.delete();
    }

    @Transactional
    public void delete(Long id) {
        InStockBook inStockBook = findById(id);
        inStockBook.delete();
    }

    @Transactional(readOnly = true)
    public ResPageDTO findByTitle(String title, Pageable pageable) {
        Page<InStockBook> page = inStockBookRepository.findByTitleContaining(title, pageable);
        return resPageDTO(page);
    }

    private InStockBook findById(Long id) {
        return inStockBookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    private ResPageDTO resPageDTO(Page<InStockBook> page) {
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<InStockBookDTO> books = page.getContent()
                .stream().map(InStockBookDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, books);
    }

}
