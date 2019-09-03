package com.trifail.practice.streamproducer.customized;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author syoka
 */
public interface Sender {

    String OUTPUT = "exe.rabbitmq.stream";

    @Output(Sender.OUTPUT)
    SubscribableChannel output();
}
