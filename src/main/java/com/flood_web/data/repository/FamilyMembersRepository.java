package com.flood_web.data.repository;

import com.flood_web.data.entity.FamilyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMembersRepository extends CrudRepository<FamilyMemberEntity, String> { // Cambia String a Long

    @Query("SELECT fm FROM FamilyMemberEntity fm " +
            "JOIN fm.family f " +
            "JOIN f.zone z " +
            "JOIN z.river r " +
            "JOIN r.sensor s " +
            "WHERE s.id = :sensorId AND fm.active = true")
    List<FamilyMemberEntity> findBySensorIdAndActive(@Param("sensorId") String sensorId);

    boolean existsByCedula(String cedula);
}