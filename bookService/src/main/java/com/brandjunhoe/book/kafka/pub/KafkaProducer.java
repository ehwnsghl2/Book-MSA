package com.brandjunhoe.book.kafka.pub;

import com.brandjunhoe.book.book.domain.event.BookCreateChanged;
import com.brandjunhoe.book.book.domain.event.BookDeleteChanged;
import com.brandjunhoe.book.book.domain.event.BookUpdateChanged;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    private static final String TOPIC_CATALOG = "book_catalog";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String message) {
        System.out.println("Produce message : " + message);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_CATALOG, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                String g = result.getProducerRecord().value();
                System.out.println("Sent message=[" + g + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @EventListener
    public void sendBookCreateEvent(BookCreateChanged bookCreateChanged) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(bookCreateChanged);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_CATALOG, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                String g = result.getProducerRecord().value();
                System.out.println("Sent message=[" + g + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @EventListener
    public void sendBookUpdateEvent(BookUpdateChanged bookUpdateChanged) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(bookUpdateChanged);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_CATALOG, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                String g = result.getProducerRecord().value();
                System.out.println("Sent message=[" + g + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @EventListener
    public void sendBookDeleteEvent(BookDeleteChanged bookDeleteChanged) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(bookDeleteChanged);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_CATALOG, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                String g = result.getProducerRecord().value();
                System.out.println("Sent message=[" + g + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }


}
