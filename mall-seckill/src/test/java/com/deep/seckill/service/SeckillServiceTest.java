package com.deep.seckill.service;

import com.deep.seckill.model.params.DoSeckillParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 秒杀
 *
 * @author Deep
 * @date 2022/5/2
 */
@SpringBootTest
class SeckillServiceTest {
    /**
     * 最大线程数
     */
    private final static int MAX_THREAD = 50;

    @Autowired
    private SeckillService seckillService;

    @Test
    void doSeckill() throws InterruptedException {
        // 倒数闩
        CountDownLatch latch = new CountDownLatch(MAX_THREAD);

        DoSeckillParam param = new DoSeckillParam();
        param.setSessionId(1L);
        param.setSkuId(16L);
        param.setCount(2);

        Runnable runnable = () -> {
            System.out.println(seckillService.doSeckill(param));
            latch.countDown();
        };

        for (int i = 0; i < MAX_THREAD; i++) {
            new Thread(runnable).start();
        }
        latch.await();
    }
}