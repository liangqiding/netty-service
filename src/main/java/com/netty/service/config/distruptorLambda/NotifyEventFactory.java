package com.netty.service.config.distruptorLambda;

import com.lmax.disruptor.EventFactory;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-02 15:22
 * @version v1.0.0
 */
public class NotifyEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new NotifyEvent();
    }
}
