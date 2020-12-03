package com.netty.service.dao;

import com.netty.service.domain.Device;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends ReactiveMongoRepository<Device, String> {
}
