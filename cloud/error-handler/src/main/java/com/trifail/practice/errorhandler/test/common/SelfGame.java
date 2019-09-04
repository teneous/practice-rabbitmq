package com.trifail.practice.errorhandler.test.common;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author syoka
 */
public interface SelfGame {

    String INPUT = "stream_game_consumer";
    String OUTPUT = "stream_game_producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
