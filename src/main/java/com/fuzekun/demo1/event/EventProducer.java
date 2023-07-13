package com.fuzekun.demo1.event;

import com.alibaba.fastjson.JSONObject;
import com.fuzekun.demo1.entity.community.Event;
import com.fuzekun.demo1.entity.community.SendMailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        // 将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

    // 发送事件-邮件发送
    public void fireEvent(SendMailEvent event) {
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
