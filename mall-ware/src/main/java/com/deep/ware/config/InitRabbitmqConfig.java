package com.deep.ware.config;

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
     * 库存服务topic交换机
     */
    @Bean
    public Exchange stockEventExchange() {
        return new TopicExchange("stock-event-exchange", true, false);
    }

    /**
     * 延迟队列
     */
    @Bean
    public Queue stockDelay() {
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", "stock-event-exchange");
        arguments.put("x-dead-letter-routing-key", "stock.release");
        // 消息过期时间 2分钟
        arguments.put("x-message-ttl", 120000);
        return new Queue("stock.delay.queue", true, false, false, arguments);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue("stock.release.stock.queue", true, false, false);
    }

    /**
     * 交换机与延迟队列绑定
     */
    @Bean
    public Binding stockLockedBinding() {
        return new Binding("stock.delay.queue", Binding.DestinationType.QUEUE, "stock-event-exchange", "stock.locked",
            null);
    }

    /**
     * 交换机与死信队列绑定
     */
    @Bean
    public Binding stockLocked() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE, "stock-event-exchange",
            "stock.release.#", null);
    }
}
