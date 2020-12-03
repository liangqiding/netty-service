package com.netty.service.config.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.Iterator;
import java.util.List;

/**
 * description : TODO Disruptor队列操作工具类
 *
 * @author : qiDing
 * date: 2020-12-01 15:46
 * @version v1.0.0
 */
public class DisruptorQueue<T> {
    private Disruptor<ObjectEvent<T>> disruptor;
    private RingBuffer<ObjectEvent<T>> ringBuffer;

    public DisruptorQueue(Disruptor<ObjectEvent<T>> disruptor) {
        this.disruptor = disruptor;
        this.ringBuffer = disruptor.getRingBuffer();
        this.disruptor.start();
    }

    public void add(T t) {
        if (t != null) {
            long sequence = this.ringBuffer.next();
            try {
                ObjectEvent<T> event = (ObjectEvent)this.ringBuffer.get(sequence);
                event.setObj(t);
//                ringBuffer.publishEvent((event, sequence2, data) -> event.setMsg(t));
            } finally {
                this.ringBuffer.publish(sequence);
            }
        }
    }

    public void addAll(List<T> ts) {
        if (ts != null) {
            Iterator<T> var2 = ts.iterator();
            while(var2.hasNext()) {
                T t = var2.next();
                if (t != null) {
                    this.add(t);
                }
            }
        }
    }

    public long cursor() {
        return this.disruptor.getRingBuffer().getCursor();
    }

    public void shutdown() {
        this.disruptor.shutdown();
    }

    public Disruptor<ObjectEvent<T>> getDisruptor() {
        return this.disruptor;
    }

    public void setDisruptor(Disruptor<ObjectEvent<T>> disruptor) {
        this.disruptor = disruptor;
    }

    public RingBuffer<ObjectEvent<T>> getRingBuffer() {
        return this.ringBuffer;
    }

    public void setRingBuffer(RingBuffer<ObjectEvent<T>> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
}
