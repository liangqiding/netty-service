package com.netty.service.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-11-18 15:56
 * @version v1.0.0
 */
@Component
@ConditionalOnExpression(value = "${zookeeper.enable}")
public class ZkApi {

    private static final Logger logger = LoggerFactory.getLogger(ZkApi.class);

    private static ZooKeeper zkClient;

    @Autowired
    private void set(ZooKeeper zc) {
        zkClient = zc;
    }

    private static String address;

    private static String port;

    @Value("${netty.port}")
    private void setPort(String p) {
        port = p;
    }

    static {
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建节点
     * <p>
     * 1、PERSISTENT
     * <p>
     * 持久化目录节点，存储的数据不会丢失。
     * <p>
     * <p>
     * 2、PERSISTENT_SEQUENTIAL
     * <p>
     * 顺序自动编号的持久化目录节点，存储的数据不会丢失，并且根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名。
     * <p>
     * <p>
     * 3、EPHEMERAL
     * <p>
     * 临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除。
     * <p>
     * <p>
     * 4、EPHEMERAL_SEQUENTIAL
     * <p>
     * 临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除，并且根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名。
     */

    @PostConstruct
    public void init() throws Exception {
        String path = "/netty";
        String service = "/netty/service";
        logger.info("【执行初始化测试方法。。。。。。。。。。。。】");
        createNode(path, "", CreateMode.PERSISTENT);
        createNode(service + ":" + address + ":" + port+"#","registered", CreateMode.EPHEMERAL_SEQUENTIAL);
        String value = getData(path, new WatcherApi());
        logger.info("【执行初始化测试方法getData返回值。。。。。。。。。。。。】={}", value);
    }

    public boolean createNode(String path, String data, CreateMode createMode) throws Exception {
        try {
            if (null == zkClient.exists(path, false)) {
                zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
                logger.info("===目录创建成功===" + path);
            } else {
                logger.error("***目录已存在***");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("重连");
        }
    }

    /**
     * 判断指定节点是否存在
     *
     * @param path
     * @param needWatch 指定是否复用zookeeper中默认的Watcher
     * @return
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return zkClient.exists(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("【断指定节点是否存在异常】{},{}", path, e);
            return null;
        }
    }

    /**
     * 检测结点是否存在 并设置监听事件
     * 三种监听类型： 创建，删除，更新
     *
     * @param path
     * @param watcher 传入指定的监听类
     * @return
     */
    public Stat exists(String path, Watcher watcher) {
        try {
            return zkClient.exists(path, watcher);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("【断指定节点是否存在异常】{},{}", path, e);
            return null;
        }
    }


    /**
     * 修改持久化节点
     *
     * @param path `
     * @param data `
     */
    public boolean updateNode(String path, String data) {
        try {
            //zk的数据版本是从0开始计数的。如果客户端传入的是-1，则表示zk服务器需要基于最新的数据进行更新。如果对zk的数据节点的更新操作没有原子性要求则可以使用-1.
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zkClient.setData(path, data.getBytes(), -1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("【修改持久化节点异常】{},{},{}", path, data, e);
            return false;
        }
    }

    /**
     * 删除持久化节点
     *
     * @param path `
     */
    public boolean deleteNode(String path) {
        try {
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zkClient.delete(path, -1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("【删除持久化节点异常】{},{}", path, e);
            return false;
        }
    }

    /**
     * 获取当前节点的子节点(不包含孙子节点)
     *
     * @param path 父节点path
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> list = zkClient.getChildren(path, false);
        return list;
    }

    /**
     * 获取指定节点的值
     *
     * @param path
     * @return
     */
    public String getData(String path, Watcher watcher) {
        try {
            Stat stat = new Stat();
            byte[] bytes = zkClient.getData(path, watcher, stat);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}


