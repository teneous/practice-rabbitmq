package com.trifail.practice.errorhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试流程:
 * 1.测试自定义错误handler -》errorHandler
 * 前置准备: 注释掉properties中的dlq(死信队列)
 *
 * 2.测试全局错误handler -》errorChannel
 * 前置准备: 注释掉properties中的dlq(死信队列)
 *
 * 3.测试死信队列处理
 * 前置准备：解除dlq注释，删除对应exchange和queue，并注释error的handler
 *
 * 发消息通过运行单元测试进行。一次单元测试为发送一条消息。消费端这边消费第二条消息的时候则会报错。
 */
@SpringBootApplication
public class ErrorHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlerApplication.class, args);
    }

}
