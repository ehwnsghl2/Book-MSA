package com.brandjunhoe.book.kafka.sub;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

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

}
