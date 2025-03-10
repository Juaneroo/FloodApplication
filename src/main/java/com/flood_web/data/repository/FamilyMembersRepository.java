package com.flood_web.data.repository;

import com.flood_web.data.entity.FamilyMembersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMembersRepository extends CrudRepository<FamilyMembersEntity, String> {

    @Query("SELECT fm FROM FamilyMembersEntity fm " +
            "JOIN fm.family f " +
            "JOIN f.zone z " +
            "JOIN z.river r " +
            "JOIN r.sensor s " +
            "WHERE s.id = :sensorId")
    List<FamilyMembersEntity> findBySensorId(@Param("sensorId") String sensorId);
}