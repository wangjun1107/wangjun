package com.wangjun.modules.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <标题>
 *
 * @author wangjun
 * 2021-08-15
 **/
@Slf4j
@RestController
public class ZookeeperController {

    @GetMapping("/zk-lock")
    public String zkLock() {
        log.info("进入了方法");
        try (ZooKeeperLock zooKeeperLock = new ZooKeeperLock()) {
            if (zooKeeperLock.getLock("CREATE-ORDER-KEY")) {
                log.info("获取zookeeper锁进入");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成！");
        return "方法执行完成！";
    }
}
