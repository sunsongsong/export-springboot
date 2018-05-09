package com.song.export.config.rabbit.test.one2more;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "one2more")
public class One2MoreReceiver2 {

    @RabbitHandler
    public void process(String helloQueue) {
        System.out.println("One2MoreReceiver2  : " + helloQueue);
    }

}
