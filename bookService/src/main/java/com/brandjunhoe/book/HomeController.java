package com.brandjunhoe.book;

import com.brandjunhoe.book.kafka.pub.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RefreshScope
public class HomeController {

    private final KafkaProducer producer;

    @Value("${server.port}")
    private int port;

    @Value("${spring.message}")
    private String message;

    public HomeController(KafkaProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/book/detail")
    public String contents() {
        return "Book Detail - Port " + port + " - " + message;
    }


    @GetMapping("/user/detail")
    public String member() {
        return "User Detail - Port " + port + " - " + message;
    }

    @GetMapping("/pay/detail")
    public String pay() {
        return "Pay Detail - Port " + port + " - " + message;
    }

    @GetMapping("/comment")
    public String comment() {
        return "Comment - Port " + port + " - " + message;
    }

    @GetMapping("/board")
    public String userDetail() {
        return "Board - Port " + port + " - " + message;
    }


    @GetMapping("/kafka")
    public void kafka() {
        producer.sendMessage("메시지 test");
    }


}
