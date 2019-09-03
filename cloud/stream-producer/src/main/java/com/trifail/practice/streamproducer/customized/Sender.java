package com.trifail.practice.streamproducer.customized;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author syoka
 */
public interface Sender {

    String OUTPUT = "exe_rabbitmq_stream";

    @Output(Sender.OUTPUT)
    SubscribableChannel output();
}
