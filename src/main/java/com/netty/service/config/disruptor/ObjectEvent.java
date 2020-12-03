package com.netty.service.config.disruptor;

import lombok.Data;

/**
 * description : TODO 事件对象
 *
 * @author : qiDing
 * date: 2020-12-01 15:44
 * @version v1.0.0
 */
@Data
public class ObjectEvent<T> {
    private T obj;

    private Object msg;

}
