package com.deep.ware.handler;

import com.deep.common.model.dto.OrderTaskDetailDto;
import com.deep.ware.service.WareSkuService;
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
@RabbitListener(queues = "stock.release.stock.queue")
public class WareRabbitmqHandler {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 超时解锁
     */
    @RabbitHandler
    public void stockReleaseHandler(OrderTaskDetailDto orderTaskDetailDto, Message message, Channel channel)
            throws IOException {
        log.info("准备解锁库存:{}", orderTaskDetailDto.getOrderSn());

        try {
            wareSkuService.unlockInventory(orderTaskDetailDto.getId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("解锁成功!" + orderTaskDetailDto.getOrderSn());
        } catch (IOException e) {
            // 消息拒绝解锁(重新放回队列)
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }
}
