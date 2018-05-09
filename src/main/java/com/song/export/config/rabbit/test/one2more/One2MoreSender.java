package com.song.export.config.rabbit.test.one2more;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class One2MoreSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg + new Date();
        System.out.println("One2MoreSender : " + sendMsg);
        this.rabbitTemplate.convertAndSend("one2more", sendMsg);
    }
}
