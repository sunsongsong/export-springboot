package com.song.export.config.rabbit.test.one2one;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class One2OneSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String sendMsg = "one2one " + new Date();
        System.out.println("One2OneSender : " + sendMsg);
        this.rabbitTemplate.convertAndSend("one2one", sendMsg);
    }


}
