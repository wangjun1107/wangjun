package com.wangjun;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wangjun.modules.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

/**
 * 创建并发测试
 * 并发测试
 *
 * @author wangjun
 * 2021-08-14
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BinFa {

    @Autowired
    private OrderService orderService;

    @Test
    public void concurrentOrder() throws InterruptedException {

        // 创建一个闭锁 5个线程
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i<5; i++){
            es.execute(() ->{
                try {
                    Thread.sleep(1000);
                    //  cyclicBarrier new 了5个线程 达到了5个线程 同时执行 保证 5个线程是并发
                    cyclicBarrier.await();
//                    Integer orderId = orderService.createOrder("s");
//                    System.out.println(orderId);
                    long id = IdWorker.getId();
                    System.out.println(id);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }


            });
        }
        // 5个线程结束 执行 es.shutdown();
        cdl.await();
        es.shutdown();
    }


}
