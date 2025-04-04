package com.flood_web.data.repository;

import com.flood_web.data.entity.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertsRepository extends JpaRepository<Alerts, Long> {

    List<Alerts> findTop10ByOrderByFechaDesc();


}