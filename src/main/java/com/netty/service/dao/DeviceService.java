package com.netty.service.dao;

import com.netty.service.domain.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import java.util.Arrays;
import java.util.List;



/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-11-25 16:21
 * @version v1.0.0
 */
@Service
public class DeviceService {
    Scheduler scheduler;
    @Autowired
    DeviceRepository deviceRepository;

    public Flux<Device> findAll() {
        Flux<Device> all = deviceRepository.findAll();
        String[] arr = {"张三", "22"};
        List<String> arr2 = Arrays.asList(arr);
//
//        Disposable subscribe = all
//                .flatMap(obj -> {
//                    Device person = new Device();
//                    // 返回mono对象
//                    return Mono.just(person);
//                }).map(obj -> {
//                    Device person = new Device();
//                    // 返回普通对象
//                    return person;
//                }).subscribe();

        return deviceRepository.findAll();
    }
}
