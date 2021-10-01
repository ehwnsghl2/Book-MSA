package com.brandjunhoe.rental.kafka.sub;


import com.brandjunhoe.rental.kafka.sub.event.CreateRentalEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {


    public static final String TOPIC_RENTAL = "topic_rental";

    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = TOPIC_RENTAL, groupId = "rental")
    public void bookCreateChanged(String message, Acknowledgment ack) throws JsonProcessingException {

        CreateRentalEvent userIdCreated = objectMapper.readValue(message, CreateRentalEvent.class);
        eventPublisher.publishEvent(userIdCreated);
        ack.acknowledge();

    }


}
