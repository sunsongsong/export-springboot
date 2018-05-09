package com.song.export.config.rabbit.test.more2more;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class More2MoreSender2 {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg + new Date();
        System.out.println("More2MoreSender2 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("more2more", sendMsg);
    }
}
