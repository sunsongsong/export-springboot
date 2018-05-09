package com.song.export.web;

import com.song.export.config.rabbit.test.callback.CallBackSender;
import com.song.export.config.rabbit.test.fanout.FanoutSender;
import com.song.export.config.rabbit.test.topic.TopicSender;
import com.song.export.config.rabbit.test.beanSender.RabbitUser;
import com.song.export.config.rabbit.test.beanSender.UserSender;
import com.song.export.config.rabbit.test.more2more.More2MoreSender1;
import com.song.export.config.rabbit.test.more2more.More2MoreSender2;
import com.song.export.config.rabbit.test.one2more.One2MoreSender;
import com.song.export.config.rabbit.test.one2one.One2OneSender;
import com.song.export.model.common.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Rabbit的Controller
 *
 */
@RestController
@RequestMapping(value = "/rabbit", produces = "application/json;charset=utf-8")
public class RabbitController {
    @Autowired
    private One2OneSender one2OneSender;

    /**
     * 点对点测试 -- 单生产者和单消费者
     * @return
     */
    @RequestMapping(value = "/one2one",method = RequestMethod.GET)
    public String one2one() {
        one2OneSender.send();
        String msg = "点对点发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }


    /**
     * 点对多测试 -- 单生产者-多消费者
     * 说明：一个生成者的生产结果被多个消费者消费
     * @return
     */
    @Autowired
    private One2MoreSender one2MoreSender;

    @RequestMapping(value = "/one2more",method = RequestMethod.GET)
    public String one2more() {
        for(int i=0;i<10;i++){
            one2MoreSender.send("消息："+ i + " ");
        }
        String msg = "点对多发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }


    @Autowired
    private More2MoreSender1 more2MoreSender1;
    @Autowired
    private More2MoreSender2 more2MoreSender2;
    /**
     * 多对多测试 -- 多生产者-多消费者
     * 说明：多个生成者的生产结果被多个消费者消费
     * @return
     */
    @RequestMapping(value = "/more2more",method = RequestMethod.GET)
    public String more2more() {
        for(int i=0;i<10;i++){
            more2MoreSender1.send("消息："+ i + " ");
            more2MoreSender2.send("消息："+ i + " ");
        }
        String msg = "多对多发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }

    @Autowired
    private UserSender userSender;
    /**
     * 实体测试 -- 实体类传输
     * 说明：springboot完美的支持对象的发送和接收，不需要格外的配置。
            实体类（必须实现序列化接口）
     * @return
     */
    @RequestMapping(value = "/userSender",method = RequestMethod.GET)
    public String userSender() {
        RabbitUser user = new RabbitUser();//必须实现序列化接口
        user.setName("hzb");
        user.setPass("123456789");
        userSender.send(user);
        String msg = "实体对象User发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }

    @Autowired
    private TopicSender topicSender;
    /**
     * topic ExChange示例  主题转发器
     * 说明：消息通过一个特定的路由键发送到所有与绑定键匹配的队列中。
     *      https://blog.csdn.net/a491857321/article/details/50616323
     *      类似与通配符的监听
     *
     *      sender1发送的消息,routing_key是“topic.message”，
     *      所以exchange里面的绑定的binding_key是“topic.message”，topic.＃都符合路由规则;
     *      所以sender1发送的消息，两个队列都能接收到；
            sender2发送的消息，routing_key是“topic.messages”，
            所以exchange里面的绑定的binding_key只有topic.＃都符合路由规则;
            所以sender2发送的消息只有队列topic.messages能收到。
     * @return
     */
    @RequestMapping(value = "/topicSender",method = RequestMethod.GET)
    public String topicSender() {
        topicSender.send();
        String msg = "Topic消息发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }

    @Autowired
    private FanoutSender fanoutSender;
    /**
     * 广播模式 一个消息发给多个消费者使用
     * 说明：给Fanout转发器发送消息，绑定了这个转发器的所有队列都收到这个消息
     * @return
     */
    @RequestMapping(value = "/fanoutSender",method = RequestMethod.GET)
    public String fanoutSender() {
        fanoutSender.send();
        String msg = "Fanout消息发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }

    @Autowired
    private CallBackSender callBackSender;

    /**
     * 带回调的消息发送
     * @return
     */
    @RequestMapping(value = "/callBackSender",method = RequestMethod.GET)
    public String callBackSender() {
        callBackSender.send();
        String msg = "callBackSender消息发送成功,执行结果请看控制台";
        return JsonResult.okResult(msg);
    }

}
