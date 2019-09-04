package com.trifail.practice.errorhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试流程:
 * 1.测试自定义错误handler -》
 * 前置准备: 注释掉properties中的dlq(死信队列)
 *
 *
 * 2.测试当消费失败时，将此消息转移到死信队列中
 */
@SpringBootApplication
public class ErrorHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlerApplication.class, args);
    }

}
