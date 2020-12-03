package com.netty.service.config.distruptorLambda;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.log4j.Log4j2;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-02 15:23
 * @version v1.0.0
 */
@Log4j2
public class NotifyEventHandlerException implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable throwable, long sequence, Object event) {
        throwable.fillInStackTrace();
        log.error("process data error sequence ==[{}] event==[{}] ,ex ==[{}]", sequence, event.toString(), throwable.getMessage());
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        log.error("start disruptor error ==[{}]!", throwable.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.error("shutdown disruptor error ==[{}]!", throwable.getMessage());
    }
}
