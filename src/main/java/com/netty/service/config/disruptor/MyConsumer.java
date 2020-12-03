package com.netty.service.config.disruptor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-01 16:29
 * @version v1.0.0
 */
@Component
public class MyConsumer extends ADisruptorConsumer<String> {


    @Override
    @Async()
    public void consumer(Object data) throws InterruptedException {
//        System.out.println(now() + this.name + "：拿到队列中的数据：" + data);
        this.test(data);
    }

    @Async()
    public void test(Object data) throws InterruptedException {
        System.out.println(now() + Thread.currentThread().getName() + "：拿到队列中的数据：" + data);
     }
    /**
     * 获取当前时间（分:秒）
     */
    public String now() {
        Calendar now = Calendar.getInstance();
        return "[" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND) + "] ";
    }
}
