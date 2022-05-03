package com.deep.ware.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * RabbitMQ基础配置
 * 
 * @author Deep
 * @date 2022/4/29
 */
@Slf4j
@Configuration
public class BasicRabbitmqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * JSON序列化
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate（InitRabbitConfig构造器调用完成之后执行）
     */
    @PostConstruct
    public void initRabbitTemplate() {

        // 消息抵达/抵达失败 服务器时触发回调
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            if (b) {
                log.info("消息已抵达服务器 =====>>> {}", correlationData);
            } else {
                log.error("消息路由服务器失败,数据{},失败原因{}", correlationData, s);
            }
        });

        // 消息投递队列失败时触发回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            // 需要修改数据库 消息的状态【后期定期重发消息】
            log.error("Fail Message:" + JSON.toJSONString(returnedMessage));
        });
    }
}
