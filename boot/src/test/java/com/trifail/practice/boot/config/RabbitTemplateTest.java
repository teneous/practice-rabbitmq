package com.trifail.practice.boot.config;

import com.trifail.practice.boot.BootApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.UUID;

/**
 * RabbitAdmin/RabbitTemplate测试用例
 *
 * @author syoka
 */
public class RabbitTemplateTest extends BootApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void m1() {

    }

    @Test
    public void rabbitAdminTest001() throws InterruptedException {
        final String exchangeName = "exe.rabbitadmin.chengdu";
        final String queueName = "que.rabbitadmin.chengdu";
        final String routingKey = "chengdu.#";
        Exchange exchange = new TopicExchange(exchangeName, true, false);
        rabbitAdmin.declareExchange(exchange);

        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);

        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName,
                routingKey, new HashMap<>());
        rabbitAdmin.declareBinding(binding);

        /*发送消息*/
        CorrelationData uniqueKey = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName, "chengdu.panda", "欢迎来成都", uniqueKey);
        /*睡眠2秒确认消息已经成功发出*/
        Thread.sleep(2000);
        /*接受消息*/
        Object message = rabbitTemplate.receiveAndConvert(queueName);
        System.err.println("收到的信息是:" + message);
    }

}
