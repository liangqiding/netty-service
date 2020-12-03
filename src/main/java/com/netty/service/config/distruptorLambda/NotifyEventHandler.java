package com.netty.service.config.distruptorLambda;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-02 15:22
 * @version v1.0.0
 */
public class NotifyEventHandler implements EventHandler<NotifyEvent>, WorkHandler<NotifyEvent> {

    @Override
    public void onEvent(NotifyEvent notifyEvent, long l, boolean b) throws Exception {
        System.out.println("接收到消息");
        this.onEvent(notifyEvent);

    }

    @Override
    public void onEvent(NotifyEvent notifyEvent) throws Exception {
        System.out.println(notifyEvent+">>>"+ UUID.randomUUID().toString());
    }
}
