package com.trifail.practice.boot.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void testSender() throws InterruptedException {
        Map<String, Object> messageProperties = new HashMap<>();

        messageProperties.put("author", "syoka");
        messageProperties.put("create_time", "2019-09-01");
        sender.send("send form producer", messageProperties);
        /*防止因为程序运行完了导致消息confirmback失败*/
        Thread.sleep(2000);
    }

}
