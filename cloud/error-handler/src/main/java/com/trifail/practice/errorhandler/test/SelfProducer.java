package com.trifail.practice.errorhandler.test;

import com.trifail.practice.errorhandler.test.common.SelfGame;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

/**
 * 自产自用
 *
 * @author syoka
 */
@EnableBinding(value = SelfGame.class)
public class SelfProducer {

//    @Bean
//    @InboundChannelAdapter(value = SelfGame.OUTPUT)
//    public MessageSource<String> timerMessageSource() {
//        return () -> new GenericMessage<>("{\"name\":\"syoka\", \"age\":25}");
//    }
}
