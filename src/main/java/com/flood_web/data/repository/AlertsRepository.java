package com.flood_web.data.repository;

import com.flood_web.controller.Alert;
import com.flood_web.data.entity.AlertsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertsRepository extends JpaRepository<AlertsEntity, Long> {

    List<AlertsEntity> findAllByOrderByDateDesc();

}