package com.trifail.practice.admintemplate.converter;

import com.trifail.practice.admintemplate.utils.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.StandardCharsets;

public class TextConverter implements MessageConverter {

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        String contentType = messageProperties.getContentType();
        if (StringUtils.isNotEmpty(contentType)) {
            if ("text".equals(contentType)) {
                return new Message(object.toString().getBytes(), messageProperties);
            }
        }
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        MessageProperties messageProperties = message.getMessageProperties();
        if (messageProperties != null) {
            String contentType = messageProperties.getContentType();
            if (StringUtils.isNotEmpty(contentType) && contentType.contains("text")) {
                return new String(message.getBody(), StandardCharsets.UTF_8);
            }
        }
        return null;
    }
}
