package com.netty.service.dao;

import com.netty.service.domain.Msgrecord;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-11-25 11:19
 * @version v1.0.0
 */
@Repository
public interface PersonRepository extends ReactiveMongoRepository<Msgrecord, String> {

//    /**
//     * 根据name查找Person
//     *
//     * @param name
//     * @return
//     */
//    Flux<Msgrecord> findByUserName(String name);
}
