package com.trifail.practice.boot.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ProducerConfig {

    @Bean
    public Exchange confirmExchange() {
        return new TopicExchange("exe.springboot.confirm", true, false);
    }

    @Bean
    public Queue confirmQueue() {
        return new Queue("que.springboot.confirm", true, false, false);
    }

    @Bean
    public Binding confirmBinding() {
        return new Binding("que.springboot.confirm", Binding.DestinationType.QUEUE, "exe.springboot.confirm",
                "springboot.#", new HashMap<>());
    }
}
