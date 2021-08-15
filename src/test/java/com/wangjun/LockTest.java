package com.wangjun;

import com.wangjun.modules.lock.ZooKeeperLock;
import com.wangjun.modules.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class LockTest {

    @Test
    public void zookeeperLock() throws Exception {
        ZooKeeperLock zooKeeperLock = new ZooKeeperLock();
        Boolean lock = zooKeeperLock.getLock("createOrder");
        log.info("获取zookeeper锁：{}",lock);
        zooKeeperLock.close();
    }
}
