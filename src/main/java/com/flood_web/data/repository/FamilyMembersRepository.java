package com.flood_web.data.repository;

import com.flood_web.data.entity.FamilyMemberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMembersRepository extends CrudRepository<FamilyMemberEntity, String> {

    @Query("SELECT fm FROM FamilyMemberEntity fm " +
            "JOIN fm.family f " +
            "JOIN f.zone z " +
            "JOIN z.river r " +
            "JOIN r.sensor s " +
            "WHERE s.id = :sensorId")
    List<FamilyMemberEntity> findBySensorId(@Param("sensorId") String sensorId);
}