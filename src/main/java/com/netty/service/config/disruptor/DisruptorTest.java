package com.netty.service.config.disruptor;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-01 16:30
 * @version v1.0.0
 */
public class DisruptorTest {
    public static void main(String[] args) throws InterruptedException {

        // 创建一个消费者
        MyConsumer myConsumer = new MyConsumer();

        // 创建一个Disruptor队列操作类对象（RingBuffer大小为4，false表示只有一个生产者）
        DisruptorQueue<String> disruptorQueue = DisruptorQueueFactory.getHandleEventsQueue(1024,
                true, myConsumer);

        // 创建一个生产者，开始模拟生产数据
        MyProducerThread myProducerThread = new MyProducerThread("===生产者1===", disruptorQueue);
        MyProducerThread myProducerThread2 = new MyProducerThread("===生产者2===", disruptorQueue);
        Thread t1 = new Thread(myProducerThread);
        Thread t2 = new Thread(myProducerThread2);
        t1.start();
        t2.start();
        // 执行3s后，生产者不再生产
        Thread.sleep(1 * 1000);
        myProducerThread.stopThread();
        myProducerThread2.stopThread();
    }
}
