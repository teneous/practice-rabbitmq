package com.trifail.cloud.streamproducer.customized;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author syoka
 */
public interface Receiver {

    /*exchanges声明*/
    String INPUT = "exe_rabbitmq_stream";

    @Input(Receiver.INPUT)
    SubscribableChannel input();
}
