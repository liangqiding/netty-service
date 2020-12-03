package com.netty.service.config.disruptor;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-01 16:29
 * @version v1.0.0
 */
@Service
public class MyProducerThread implements Runnable {
    private final String name;
    private final DisruptorQueue<String> disruptorQueue;
    private volatile boolean flag = true;
    private static final AtomicInteger COUNT = new AtomicInteger();

    public MyProducerThread() {
        this.name = Thread.currentThread().getName();
        MyConsumer myConsumer = new MyConsumer();
        this.disruptorQueue = DisruptorQueueFactory.getHandleEventsQueue(1024,
                true, myConsumer);
    }

    public MyProducerThread(String name, DisruptorQueue<String> disruptorQueue) {
        this.name = name;
        this.disruptorQueue = disruptorQueue;
    }

    public void addQuery(Object msg) {
        disruptorQueue.add(msg.toString());
        System.out.println(now() + this.name + "：存入" + msg + "到队列中。");
    }

    @Override
    public void run() {
        try {
            System.out.println(now() + this.name + "：线程启动。");
            while (flag) {
                String data = COUNT.incrementAndGet() + "";
                // 将数据存入队列中
                disruptorQueue.add(data);
                System.out.println(now() + this.name + "：存入" + data + "到队列中。");
            }
        } catch (Exception ignored) {

        } finally {
            System.out.println(now() + this.name + "：退出线程。");
        }
    }

    public void stopThread() {
        this.flag = false;
    }

    /**
     * 获取当前时间（分:秒）
     */
    public String now() {
        Calendar now = Calendar.getInstance();
        return "[" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND) + "] ";
    }
}
