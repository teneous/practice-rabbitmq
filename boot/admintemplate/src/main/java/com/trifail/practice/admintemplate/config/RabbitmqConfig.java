package com.trifail.practice.admintemplate.config;

import com.trifail.practice.admintemplate.delegate.ChengDuDelegate;
import com.trifail.practice.admintemplate.domain.City;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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


        /*json转换器*/
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        /*json实体转换器*/
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        /*header设值   Jackson2JavaTypeMapper.TypePrecedence#TYPE_ID}*/
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("city", City.class);
        typeMapper.setIdClassMapping(classMap);

        jackson2JsonMessageConverter.setJavaTypeMapper(typeMapper);
        listenerAdapter.setMessageConverter(jackson2JsonMessageConverter);

        /*设置代理实体*/
        listenerAdapter.setDelegate(new ChengDuDelegate());
        listenerAdapter.addQueueOrTagToMethodName("que.chengdu", "messageHandler");
        listenerContainer.setMessageListener(listenerAdapter);
        return listenerContainer;
    }
}
