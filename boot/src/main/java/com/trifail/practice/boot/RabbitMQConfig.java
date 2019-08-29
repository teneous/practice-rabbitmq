//package com.trifail.practice.boot;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ComponentScan({"com.trifail.rabbitmq.*"})
//public class RabbitMQConfig {
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost("111.231.69.73");
//        factory.setPort(5672);
//        factory.setVirtualHost("my_vhost");
//        factory.setUsername("admin");
//        factory.setPassword("admin");
//        return factory;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }
//
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
//
//    @Bean
//    public Exchange exchange001() {
//        return new TopicExchange("topic_001", true, false);
//    }
//
//    @Bean
//    public Queue queue001() {
//        return new Queue("queue_001", true);
//    }
//
//    @Bean
//    public Binding binding001() {
//        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*").and(null);
////        BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
////        return new Binding("topic_001", Binding.DestinationType.QUEUE, "queue_001", "spring.*", new HashMap<>())
//    }
//
//    /**
//     * 简单消息监听
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
//        /*设置监听队列*/
//        listenerContainer.setQueues(queue001());
//        /*是否重新入队*/
//        listenerContainer.setDefaultRequeueRejected(false);
//        /*手动签收*/
//        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        /*设置消费者数量*/
//        listenerContainer.setConcurrentConsumers(50);
//        /*设置消息监听器*/
//        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter();
//        /*设置代理对象，注意此处的delegate对象内部必须有queue1messageHandler，如果没有设置将查找handleMessage方法*/
//        listenerAdapter.setMessageConverter(new Queue001MessageConverter());
//        listenerAdapter.setDelegate(new Queue1Delegate());
//        listenerAdapter.setDefaultListenerMethod("queue1MessageHandler");
//        listenerAdapter.addQueueOrTagToMethodName("queue_001", "queue1MessageHandler");
//        listenerContainer.setMessageListener(listenerAdapter);
//        return listenerContainer;
//    }
//
//}
//
//
