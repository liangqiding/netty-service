package com.netty.service.config.disruptorStream;

/**
 * description : TODO 事件对象
 *
 * @author : qiDing
 * date: 2020-12-01 15:44
 * @version v1.0.0
 */
public class ObjectEvent<T> {
    private T obj;

    public ObjectEvent() {
    }

    public T getObj() {
        return this.obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
