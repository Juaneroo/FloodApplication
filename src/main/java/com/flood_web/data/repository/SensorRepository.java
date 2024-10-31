package com.flood_web.data.repository;

import com.flood_web.data.entity.SensorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorRepository extends CrudRepository<SensorEntity, UUID> {
}
