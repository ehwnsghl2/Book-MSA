package com.brandjunhoe.book.book.application;

import com.brandjunhoe.book.book.application.dto.BookDTO;
import com.brandjunhoe.book.book.domain.event.InStockBooDeleteEvent;
import com.brandjunhoe.book.book.presentation.dto.ReqBookRegistDTO;
import com.brandjunhoe.book.book.presentation.dto.ReqBookUpdateDTO;
import com.brandjunhoe.book.common.exception.DataNotFoundException;
import com.brandjunhoe.book.common.page.ResPageDTO;
import com.brandjunhoe.book.common.page.TotalPageDTO;
import com.brandjunhoe.book.book.domain.Book;
import com.brandjunhoe.book.book.domain.BookRepository;
import com.brandjunhoe.book.book.domain.BookStatus;
import com.brandjunhoe.book.book.domain.event.BookCreateChanged;
import com.brandjunhoe.book.book.domain.event.BookDeleteChanged;
import com.brandjunhoe.book.book.domain.event.BookUpdateChanged;
import org.springframework.context.ApplicationEventPublisher;
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
public class BookService {

    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookService(BookRepository bookRepository, ApplicationEventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void save(BookDTO bookDTO) {
        bookRepository.save(bookDTO.toEntity());
    }

    @Transactional(readOnly = true)
    public ResPageDTO findAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        TotalPageDTO total = new TotalPageDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        List<BookDTO> books = page.getContent()
                .stream().map(BookDTO::new).collect(Collectors.toList());

        return new ResPageDTO(total, books);
    }

    @Transactional(readOnly = true)
    public BookDTO findOne(Long id) {
        return bookRepository.findById(id).map(BookDTO::new)
                .orElseThrow(() -> new DataNotFoundException("book not content"));
    }

    @Transactional
    public void delete(Long id) {
        Book book = findById(id);
        book.delete();
        sendBookDeleteCatalogEvent(book.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void createBook(BookDTO bookDTO) {
        Book book = bookRepository.save(bookDTO.toEntity());
        sendNewBookCatalogEvent(book);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBook(Long id, ReqBookUpdateDTO updateDTO) {

        Book updatedBook = findById(id);

        updatedBook.update(updateDTO.getTitle(), updateDTO.getDescription(), updateDTO.getAuthor(),
                updateDTO.getPublisher(), updateDTO.getIsbn(), updateDTO.getClassification(),
                updateDTO.getBookStatus(), updateDTO.getLocation());

        sendUpdateBookCatalogEvent(updatedBook);

    }

    @Transactional
    public void processChangeBookState(Long id, BookStatus bookStatus) {
        Book book = findById(id);
        book.bookStatusChanged(bookStatus);
    }

    @Transactional(rollbackFor = Exception.class)
    public void registerNewBook(ReqBookRegistDTO registDTO, Long inStockId) {
        Book newBook = bookRepository.save(registDTO.toEntity());

        eventPublisher.publishEvent(new InStockBooDeleteEvent(inStockId));

        sendNewBookCatalogEvent(newBook); //send kafka - bookcatalog
    }


    private Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }


    public void sendNewBookCatalogEvent(Book book) {

        BookCreateChanged createChanged = BookCreateChanged.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publicationDate(book.getPublicationDate().format(fmt))
                .classification(book.getClassification())
                .rentCnt((long) 0)
                .rented(!book.getBookStatus().equals(BookStatus.AVAILABLE))
                .build();

        eventPublisher.publishEvent(createChanged);

    }

    public void sendUpdateBookCatalogEvent(Book book) {

        BookUpdateChanged updateChanged = BookUpdateChanged.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publicationDate(book.getPublicationDate().format(fmt))
                .classification(book.getClassification())
                .rentCnt((long) 0)
                .rented(!book.getBookStatus().equals(BookStatus.AVAILABLE))
                .build();

        eventPublisher.publishEvent(updateChanged);

    }

    public void sendBookDeleteCatalogEvent(Long bookId) {
        eventPublisher.publishEvent(new BookDeleteChanged(bookId));
    }

}
