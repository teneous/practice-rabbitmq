package com.trifail.cloud.streamproducer.customized;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author syoka
 */
public interface Receiver {

    String INPUT = "exe.rabbitmq.stream";

    @Input(Receiver.INPUT)
    SubscribableChannel input();
}
