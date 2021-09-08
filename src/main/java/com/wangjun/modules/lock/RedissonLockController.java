package com.wangjun.modules.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <标题>
 *
 * @author wangjun
 * 2021-08-21
 **/
@Slf4j
@RestController
public class RedissonLockController {

    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/redisson-lock")
    public String testRedissonLock() throws InterruptedException {
        log.info("进入方法体");
        RLock rLock = redissonClient.getLock("order-submit");
        try {
            rLock.lock();
            log.info("执行业务流程");
            Thread.sleep(1000);
            log.info("执行业务流程结束返回");
            return "流程结束拿到了锁";
        }finally {
            if(rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                log.info("释放锁");
                rLock.unlock();
            }
        }
    }

    @GetMapping("/redisson-try-lock")
    public String testRedissonTryLock() {
        log.info("进入方法体");
        RLock rLock = redissonClient.getLock("order-submit-tryLock");
        try {
            boolean lock = rLock.tryLock();
            if(lock) {
                log.info("加锁成功-执行业务流程");
                Thread.sleep(31000);

            }else {
                log.info("加锁失败！");
                return "加锁失败";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            log.info("执行业务流程结束返回 释放锁");
            if(rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }
        return "流程结束拿到了锁";
    }

}
