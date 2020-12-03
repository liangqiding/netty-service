package com.netty.service.config.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;


/**
 * description : TODO 消费者抽象类
 *
 * @author : qiDing
 * date: 2020-12-01 15:49
 * @version v1.0.0
 */

public abstract class ADisruptorConsumer<T>
        implements EventHandler<ObjectEvent<T>>, WorkHandler<ObjectEvent<T>> {
    public ADisruptorConsumer() {
    }

    @Override
    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(ObjectEvent<T> event) throws Exception {
        this.consumer(event.getObj());
    }

    /**
     * 自定义消费者
     *
     * @param var1 msg
     */
    public abstract void consumer(Object var1) throws InterruptedException;
}
