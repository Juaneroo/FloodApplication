package com.flood_web.data.repository;

import com.flood_web.data.entity.PersonRiskLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PersonRiskLogRepository extends JpaRepository<PersonRiskLogEntity, String> {

    List<PersonRiskLogEntity> findAllByOrderByDateDesc();


    List<PersonRiskLogEntity> findByDateBetweenOrderByDateDesc(LocalDateTime start, LocalDateTime end);

}
