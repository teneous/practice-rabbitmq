package com.trifail.practice.streamproducer;

import com.trifail.practice.streamproducer.customized.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamProducerApplicationTests {

    @Autowired
    private Sender streamSender;

    @Test
    public void sinkSenderTester() {
        streamSender.output().send(MessageBuilder.withPayload("hello there!").build());
    }


}
