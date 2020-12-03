package com.netty.service.config.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * description : TODO 事件生成工厂（用来初始化预分配事件对象）
 *
 * @author : qiDing
 * date: 2020-12-01 15:45
 * @version v1.0.0
 */
public class ObjectEventFactory<T> implements EventFactory<ObjectEvent<T>> {
    public ObjectEventFactory() {
    }

    @Override
    public ObjectEvent<T> newInstance() {
        return new ObjectEvent<T>();
    }
}
