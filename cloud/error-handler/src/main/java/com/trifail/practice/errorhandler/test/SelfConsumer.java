package com.trifail.practice.errorhandler.test;

import com.trifail.practice.errorhandler.test.common.SelfGame;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

/**
 * 自产自用
 *
 * @author syoka
 */
@EnableBinding(value = SelfGame.class)
public class SelfConsumer {

    private int count = 0;

    @StreamListener(SelfGame.INPUT)
    public void receive(Message message) {
        System.err.println("接受消息！！！");
        System.out.println(message);
        count++;
        if (count == 2) {
            throw new RuntimeException();
        }
    }


    /*
     * 参考文档https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/2.1.3.RELEASE/single/spring-cloud-stream.html#polled-errors
     * 参考文档7.4章
     */

    /*服务降级exchange.queue.queue*/
    @ServiceActivator(inputChannel = "games.stream-exception-handler.errors")
    public void errorHandler(Message<?> message) {
        System.err.println("消息降级处理" + message.toString());
    }


    /*全局异常处理*/
    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        System.out.println("Handling ERROR: " + errorMessage);
    }

}
