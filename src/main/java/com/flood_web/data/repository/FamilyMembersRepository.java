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

    Optional<FamilyMemberEntity> findByCedula(String cedula);

    @Query("SELECT fm FROM FamilyMemberEntity fm " +
            "JOIN fm.family f " +
            "JOIN f.zone z " +
            "JOIN z.river r " +
            "JOIN r.sensor s " +
            "WHERE s.id = :sensorId")
    List<FamilyMemberEntity> findBySensorId(@Param("sensorId") String sensorId);

    // No necesitamos existsByIdNumber, pero podríamos usar existsByCedula
    boolean existsByCedula(String cedula);
}