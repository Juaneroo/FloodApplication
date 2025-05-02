package com.flood_web.data.repository;

import com.flood_web.data.entity.PersonRiskLogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRiskLogRepository extends CrudRepository<PersonRiskLogEntity, String> {
}
