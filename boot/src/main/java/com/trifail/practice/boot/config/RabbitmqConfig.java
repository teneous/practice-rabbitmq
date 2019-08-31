package com.trifail.practice.boot.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author syoka
 */
@Configuration
public class RabbitmqConfig {


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("111.231.69.73");
        factory.setPort(5672);
        factory.setVirtualHost("springboot");
        factory.setUsername("admin");
        factory.setPassword("admin");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /*###########################################################################################*/

    /*如何声明交换机，队列，绑定器*/
    @Bean
    public Exchange bootExchangeAreaChengDu() {
        return new TopicExchange("exe.chengdu", true, false);
    }

    @Bean
    public Queue bootQueueAreaChengDu() {
        return new Queue("que.chengdu", true, false, false);
    }

    @Bean
    public Binding bootBindingAreaChengDu() {
        return new Binding("que.chengdu", Binding.DestinationType.QUEUE, "exe.chengdu",
                "chengdu.#", new HashMap<>());
    }

    /*###########################################################################################*/

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        /*设置监听队列*/
        listenerContainer.setQueues(bootQueueAreaChengDu());
        /*是否重新入队*/
        listenerContainer.setDefaultRequeueRejected(false);
        /*手动签收*/
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        /*设置消费者数量*/
        listenerContainer.setConcurrentConsumers(10);
        /*设置消息监听器*/
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter();

        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        Jackson2JavaTypeMapper jackson2JavaTypeMapper = new Jackson2JavaTypeMapper();
//        jackson2JsonMessageConverter.setJavaTypeMapper();

        /*设置代理对象，注意此处的delegate对象内部必须有queue1messageHandler，如果没有设置将查找handleMessage方法*/
//        listenerAdapter.setMessageConverter();
//        listenerAdapter.setDelegate(new Queue1Delegate());
        listenerAdapter.setDefaultListenerMethod("queue1MessageHandler");
        listenerAdapter.addQueueOrTagToMethodName("queue_001", "queue1MessageHandler");
        listenerContainer.setMessageListener(listenerAdapter);
        return listenerContainer;
    }
}
