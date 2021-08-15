package com.wangjun.modules.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ZooKeeper分布式锁
 * ZooKeeperLock
 *
 * @author wangjun
 * 2021-08-15
 **/
@Slf4j
public class ZooKeeperLock implements AutoCloseable, Watcher {

    private final ZooKeeper zooKeeper;
    private String zooNode;

    public ZooKeeperLock() throws IOException {
        this.zooKeeper = new ZooKeeper("localhost:2181",30000,this);
    }

    public Boolean getLock(String businessKey){

        try {
            // 创建业务根节点
            Stat stat = zooKeeper.exists("/" + businessKey, false);
            if (Objects.isNull(stat)){
                zooKeeper.create("/"+businessKey,businessKey.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, // 开放
                        CreateMode.PERSISTENT );  // 持久节点
            }
            // 创建瞬时有序节点  （/XXX/XXX_001）
            zooNode = zooKeeper.create("/" + businessKey + "/" + businessKey + "_",
                    businessKey.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, // 开放
                    CreateMode.EPHEMERAL_SEQUENTIAL);// 瞬时节点

            // 获取子节点有序列表
            List<String> childrenNodes = zooKeeper.getChildren("/" + businessKey, false);
            Collections.sort(childrenNodes); // 排序
            String firstNode = childrenNodes.get(0); // 第一个 最小的节点
            // 是第一个节点获得锁
            if (zooNode.endsWith(firstNode)){
                return true;
            }

            String lastNode = firstNode;
            for (String node : childrenNodes) {

                // 如果创建节点相等 则创建监视器
                if (zooNode.endsWith(node)){
                    // 监视上一个节点  ，这个监听到消失就会调用 process()方法
                    zooKeeper.exists("/"+businessKey+"/"+lastNode,true);
                    break;
                }else {
                    // 否则 当前节点为上一个节点
                    lastNode = node;
                }
            }

            // 监听器创建完成 让线程等待 也就是让线程排队 等待时要加锁
            synchronized (this){
                wait();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



    @Override
    public void close() throws Exception {

        zooKeeper.delete(zooNode,-1);
        zooKeeper.close();
        log.info("zookeeper锁释放");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        // 如果监听到这个类型是NodeDeleted 节点删除 唤起上面等待线程
        if(watchedEvent.getType() == Event.EventType.NodeDeleted){
            synchronized (this) {
                notify();
            }
        }
    }
}
