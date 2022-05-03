package com.deep.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ初始化配置类
 *
 * @author Deep
 * @date 2022/5/3
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
     * 商品秒杀队列<br>
     * 作用：削峰，创建订单
     */
    @Bean
    public Queue orderSecKillOrderQueue() {
        return new Queue("order.seckill.order.queue", true, false, false);
    }

    /**
     * 秒杀队列和订单交换机绑定
     */
    @Bean
    public Binding orderSecKillOrderQueueBinding() {
        return new Binding(
                "order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                null);
    }
}
