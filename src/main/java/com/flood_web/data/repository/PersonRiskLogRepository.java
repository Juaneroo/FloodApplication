package com.flood_web.data.repository;

import com.flood_web.data.entity.PersonRiskLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PersonRiskLogRepository extends JpaRepository<PersonRiskLogEntity, String> {

    List<PersonRiskLogEntity> findAllByOrderByDateDesc();

    //Create a named query to list by a date range. Using @Query
    @Query("SELECT p FROM PersonRiskLogEntity p WHERE p.date BETWEEN :start AND :end ORDER BY p.date DESC")
    List<PersonRiskLogEntity> findByDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
