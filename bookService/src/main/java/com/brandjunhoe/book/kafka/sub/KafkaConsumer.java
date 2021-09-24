package com.brandjunhoe.book.kafka.sub;


import com.brandjunhoe.book.kafka.sub.event.StockChanged;
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
        System.out.println("Consumed book message : " + message);
        /*try {
            System.out.println("Recieved ping message: " + message);
            ack.acknowledge();
        } catch (Exception e) {
            String msg = "시스템에 예상치 못한 문제가 발생했습니다";
            System.out.println("Recieved ping message: " + msg + e);
        }*/
    }

    @KafkaListener(topics = "topic_book", groupId = "book")
    public void book(String message/*, Acknowledgment ack*/) throws JsonProcessingException {
        StockChanged stockChanged = objectMapper.readValue(message, StockChanged.class);

        eventPublisher.publishEvent(stockChanged);

    }


}
