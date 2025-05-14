package com.flood_web.data.repository;

import com.flood_web.data.entity.AdministratorsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministratorsRepository extends CrudRepository<AdministratorsEntity, String> {

    //create a query funtion that return a administrator by its cedula and if active is true
    @Query("SELECT a FROM AdministratorsEntity a WHERE a.cedula = :cedula AND a.active = true")
    Optional<AdministratorsEntity> findByCedula(@Param("cedula") String cedula);


    //create a query funtion that return that update the password of an administrator by its cedula
    @Query("UPDATE AdministratorsEntity a SET a.password = :password WHERE a.cedula = :cedula")
    void updatePassword(@Param("cedula") String cedula, @Param("password") String password);
}