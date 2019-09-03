package com.trifail.practice.streamproducer.customized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author syoka
 */
@EnableBinding(value = {Sender.class})
public class StreamSender {

    private static Logger logger = LoggerFactory.getLogger(StreamSender.class);
    private Integer count = 0;

    @Bean
    @InboundChannelAdapter(value = Sender.OUTPUT, poller = @Poller(fixedDelay = "2000"))
    public MessageSource<String> timerMessageSource() {
        return () -> new GenericMessage(count++);
//        return () -> new GenericMessage<>("{\"number\":\"" + count + "\", " + "\"name\":\"syoka\", \"age\":25}");
    }
}
