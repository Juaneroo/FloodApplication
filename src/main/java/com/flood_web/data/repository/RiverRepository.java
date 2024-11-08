package com.flood_web.data.repository;

import com.flood_web.data.entity.RiverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiverRepository extends CrudRepository<RiverEntity, String> {
}
