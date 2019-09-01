package com.trifail.practice.admintemplate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trifail.practice.admintemplate.domain.City;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

/**
 * RabbitAdmin/RabbitTemplate测试用例
 *
 * @author syoka
 */
public class RabbitTemplateTest extends AdmintemplateApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static Logger logger = LoggerFactory.getLogger(RabbitTemplateTest.class);

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


    @Test
    public void messageConverterTest001() throws JsonProcessingException {
        City city = new City();
        city.setDesc("foods,panda");
        city.setLevel(2);
        city.setNumber_code("028");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(city);
        logger.info("json源文件" + json);

        /*MessageProperties引入Content_type*/
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.getHeaders().put(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME, "city");


        Message message = new Message(json.getBytes(StandardCharsets.UTF_8), properties);
        rabbitTemplate.send("exe.chengdu", "chengdu.panda", message);
    }

}
