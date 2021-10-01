package com.brandjunhoe.bookcatalog.kafka.sub;


import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookCreateChanged;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookDeleteChanged;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.event.BookUpdateChanged;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

    private static final String TOPIC_CATALOG_CREATE = "topic_catalog_create";
    private static final String TOPIC_CATALOG_UPDATE = "topic_catalog_update";
    private static final String TOPIC_CATALOG_DELETE = "topic_catalog_delete";
    private static final String TOPIC_CATALOG_RENT = "topic_catalog_rent";
    private static final String TOPIC_CATALOG_RETURN = "topic_catalog_return";

    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = TOPIC_CATALOG_CREATE, groupId = "book_catalog")
    public void bookCreateChanged(String message, Acknowledgment ack) {

        try {
            BookCreateChanged bookCreateChanged = objectMapper.readValue(message, BookCreateChanged.class);
            eventPublisher.publishEvent(bookCreateChanged);
            ack.acknowledge();
        } catch (Exception e) {
            String msg = "시스템에 예상치 못한 문제가 발생했습니다";
            System.out.println("Recieved ping message: " + msg + e);
        }

    }

    @KafkaListener(topics = TOPIC_CATALOG_UPDATE, groupId = "book_catalog")
    public void bookUpdateChanged(String message, Acknowledgment ack) {
        try {
            BookUpdateChanged bookUpdateChanged = objectMapper.readValue(message, BookUpdateChanged.class);
            eventPublisher.publishEvent(bookUpdateChanged);
            ack.acknowledge();
        } catch (Exception e) {
            String msg = "시스템에 예상치 못한 문제가 발생했습니다";
            System.out.println("Recieved ping message: " + msg + e);
        }

    }

    @KafkaListener(topics = TOPIC_CATALOG_DELETE, groupId = "book_catalog")
    public void bookDeleteChanged(String message, Acknowledgment ack) {
        try {
            BookDeleteChanged bookDeleteChanged = objectMapper.readValue(message, BookDeleteChanged.class);
            eventPublisher.publishEvent(bookDeleteChanged);
            ack.acknowledge();
        } catch (Exception e) {
            String msg = "시스템에 예상치 못한 문제가 발생했습니다";
            System.out.println("Recieved ping message: " + msg + e);
        }

    }

}
