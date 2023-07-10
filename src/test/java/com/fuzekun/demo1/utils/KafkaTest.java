package com.fuzekun.demo1.utils;

import com.fuzekun.demo1.Demo1Application;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Zekun Fu
 * @date: 2023/1/14 10:40
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class KafkaTest {

    /*
    * 测试是否已经连接和配置成功了
    * 1. 封装生产者和消费者代码
    * 2. 生产者发送消息，生产者线程等待
    * 3.
    * */
    @Autowired
    KafkaProducer producer;
    @Autowired
    KafkaConsumer consumer;
    @Test
    public void testConenction() {
        producer.sendMSG("test", "你好");
        producer.sendMSG("test", "在吗");

        try {
            Thread.sleep(1000 * 5);        // 睡眠5s, 等待自动调用消费者线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


@Component
class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMSG(String topic, String content) {
        kafkaTemplate.send(topic, content);
    }
}

@Component
class KafkaConsumer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = {"test"})
    public void handlerMsg(ConsumerRecord record) {
//        System.out.println("自动调用了");
        System.out.println(record.value());
    }
}


