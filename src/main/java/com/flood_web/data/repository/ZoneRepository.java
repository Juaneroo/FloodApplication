package com.flood_web.data.repository;

import com.flood_web.data.entity.ZoneEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends CrudRepository<ZoneEntity, String> {
}
