package com.trifail.practice.errorhandler;

import com.trifail.practice.errorhandler.test.common.SelfGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorHandlerApplicationTests {

    @Autowired
    private SelfGame selfGame;

    @Test
    public void testContext() {
        selfGame.output().send(MessageBuilder.withPayload("\"name\":\"syoka\", \"age\":25}").build());
    }
}
