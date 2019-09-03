package com.trifail.cloud.streamproducer.customized;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Date;

/**
 * @author syoka
 */
@EnableBinding(value = Receiver.class)
public class StreamReceiver {

    @StreamListener(Receiver.INPUT)
    public void receive(Object payload) {
        System.err.println("接受消息:" + payload + ",接受时间" + new Date());
    }
}
