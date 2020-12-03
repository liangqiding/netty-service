package com.netty.service.config.disruptor;

import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.Executors;

/**
 * description : TODO Disruptor队列操作工具类工厂
 *
 * @author : qiDing
 * date: 2020-12-01 16:27
 * @version v1.0.0
 */
public class DisruptorQueueFactory {
    public DisruptorQueueFactory() {
    }
    /**
     * 创建"点对点模式"的操作队列，即同一事件会被一组消费者其中之一消费
     */
    @SafeVarargs
    public static <T> DisruptorQueue<T> getWorkPoolQueue(int queueSize, boolean isMoreProducer,
                                                         ADisruptorConsumer<T>... consumers) {
        Disruptor<ObjectEvent<T>> disruptor = new Disruptor(new ObjectEventFactory(),
                queueSize, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new SleepingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(consumers);
        return new DisruptorQueue(disruptor);
    }
    /**
     * 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
     */
    @SafeVarargs
    public static <T> DisruptorQueue<T> getHandleEventsQueue(int queueSize, boolean isMoreProducer,
                                                             ADisruptorConsumer<T>... consumers) {
        Disruptor<ObjectEvent<T>> disruptor = new Disruptor(new ObjectEventFactory(),
                queueSize, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new SleepingWaitStrategy());
        disruptor.handleEventsWith(consumers);
        return new DisruptorQueue(disruptor);
    }


    /**
     * 直接通过传入的 Disruptor 对象创建操作队列（如果消费者有依赖关系的话可以用此方法）
     */
    public static <T> DisruptorQueue<T> getQueue(Disruptor<ObjectEvent<T>> disruptor) {
        return new DisruptorQueue(disruptor);
    }
}
