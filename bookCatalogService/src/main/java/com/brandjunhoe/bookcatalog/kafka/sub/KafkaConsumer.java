package com.brandjunhoe.bookcatalog.kafka.sub;


import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookCreateChanged;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookDeleteChanged;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookUpdateChanged;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "book_catalog", groupId = "book")
    public void consume(String message/*, Acknowledgment ack*/) {
        System.out.println("Consumed book catalog message : " + message);
        /*try {
            System.out.println("Recieved ping message: " + message);
            ack.acknowledge();
        } catch (Exception e) {
            String msg = "시스템에 예상치 못한 문제가 발생했습니다";
            System.out.println("Recieved ping message: " + msg + e);
        }*/
    }

    @KafkaListener(topics = "topic_catalog_create", groupId = "book_catalog")
    public void bookCreateChanged(String message/*, Acknowledgment ack*/) throws JsonProcessingException {
        BookCreateChanged bookCreateChanged = objectMapper.readValue(message, BookCreateChanged.class);

        eventPublisher.publishEvent(bookCreateChanged);
    }

    @KafkaListener(topics = "topic_catalog_update", groupId = "book_catalog")
    public void bookUpdateChanged(String message/*, Acknowledgment ack*/) throws JsonProcessingException {
        BookUpdateChanged bookUpdateChanged = objectMapper.readValue(message, BookUpdateChanged.class);

        eventPublisher.publishEvent(bookUpdateChanged);
    }

    @KafkaListener(topics = "topic_catalog_delete", groupId = "book_catalog")
    public void bookDeleteChanged(String message/*, Acknowledgment ack*/) throws JsonProcessingException {
        BookDeleteChanged bookDeleteChanged = objectMapper.readValue(message, BookDeleteChanged.class);

        eventPublisher.publishEvent(bookDeleteChanged);
    }

}
