package com.deep.order.handler;

import com.deep.order.model.entity.OrderEntity;
import com.deep.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ消息监听
 *
 * @author Deep
 * @date 2022/4/30
 */
@Slf4j
@Component
@RabbitListener(queues = "order.release.order.queue")
public class OrderRabbitmqHandler {
    @Autowired
    private OrderService orderService;

    /**
     * 超时解锁
     */
    @RabbitHandler
    public void orderReleaseHandler(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
        log.info("收到过期的订单信息，准备关闭订单：" + orderEntity.getOrderSn());
        try {
            orderService.closeOrder(orderEntity.getOrderSn());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("关闭订单成功!" + orderEntity.getOrderSn());
        } catch (IOException e) {
            // 消息拒绝解锁(重新放回队列)
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
