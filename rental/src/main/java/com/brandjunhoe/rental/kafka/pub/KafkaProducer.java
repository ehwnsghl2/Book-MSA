package com.brandjunhoe.rental.kafka.pub;

import com.brandjunhoe.rental.rental.domain.event.BookSavePointsEvent;
import com.brandjunhoe.rental.rental.domain.event.BookRentEvent;
import com.brandjunhoe.rental.rental.domain.event.BookReturnEvent;
import com.brandjunhoe.rental.rental.domain.event.BookStcokEvent;
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

    private static final String TOPIC_BOOK = "topic_book";
    private static final String TOPIC_POINT = "topic_point";
    private static final String TOPIC_CATALOG_RENT = "topic_catalog_rent";
    private static final String TOPIC_CATALOG_RETURN = "topic_catalog_return";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    // 책 상태 업데이트
    @EventListener
    public void updateBookStatus(BookStcokEvent event) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(event);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_BOOK, message);

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

    // 권당 포인트 적립
    @EventListener
    public void savePoints(BookSavePointsEvent event) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(event);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_POINT, message);

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


    // 대출시 book catalog의 책 상태 업데이트
    @EventListener
    public void updateBookCatalogRentStatus(BookRentEvent event) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(event);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_CATALOG_RENT, message);

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

    // 반납시 book catalog의 책 상태 업데이트
    @EventListener
    public void updateBookCatalogReturnStatus(BookReturnEvent event) throws JsonProcessingException {

        String message = objectMapper.writeValueAsString(event);
        // Kafka 서버로 메시지가 전송
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC_CATALOG_RENT, message);

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
