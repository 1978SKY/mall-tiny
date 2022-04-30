package com.deep.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ初始化配置类
 * 
 * @author Deep
 * @date 2022/4/29
 */
@Configuration
public class InitRabbitmqConfig {

    /**
     * topic交换机（注入Bean时自动创建）
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 延时队列
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> arguments = new HashMap<>(3);
        // 死信路由
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        // 死信路由键
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        // 消息过期时间 2分钟(超时将会被路由到死信队列)
        arguments.put("x-message-ttl", 120000);
        return new Queue("order.delay.queue", true, false, false, arguments);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

    /**
     * 交换机与延迟队列的绑定
     */
    @Bean
    public Binding orderCreateBinding() {
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE, "order-event-exchange",
            "order.create.order", null);
    }

    /**
     * 交换机与死信队列的绑定
     */
    @Bean
    public Binding orderReleaseBinding() {
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE, "order-event-exchange",
            "order.release.order", null);
    }

    /**
     * 订单交换机和库存死信队列的绑定
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "order-event-exchange",
            "order.release.other.#", null);
    }
}
