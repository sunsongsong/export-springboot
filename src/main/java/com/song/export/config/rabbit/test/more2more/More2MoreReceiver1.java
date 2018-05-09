package com.song.export.config.rabbit.test.more2more;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "more2more")
public class More2MoreReceiver1 {

    @RabbitHandler
    public void process(String helloQueue) {
        System.out.println("More2MoreReceiver1  : " + helloQueue);
    }

}
