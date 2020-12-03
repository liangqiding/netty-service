package com.netty.service.controller;

import com.netty.service.config.disruptor.DisruptorQueue;
import com.netty.service.config.disruptor.MyProducerThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-02 16:03
 * @version v1.0.0
 */
@RestController
@Slf4j
public class DisruptorController {
    @Autowired
    MyProducerThread myProducerThread;

    @RequestMapping("/disruptor")
    public String disruptor(Long count) {
        log.info("---go---");
        for (int i = 0; i < count; i++) {
            myProducerThread.addQuery(i);
        }
        return "success";
    }
}
