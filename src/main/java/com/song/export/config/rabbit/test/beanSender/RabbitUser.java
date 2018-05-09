package com.song.export.config.rabbit.test.beanSender;

import java.io.Serializable;

/**
 *  实体类传输

    springboot完美的支持对象的发送和接收，不需要格外的配置。

    实体类（必须实现序列化接口）
 */
public class RabbitUser implements Serializable {
    private String name;
    private String pass;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
}
