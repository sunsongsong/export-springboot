package com.song.export.config.rabbit.test.one2one;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "one2one")
public class One2OneReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("One2OneReceiver  : " + hello);
    }

}
