package com.flood_web.data.repository;

import com.flood_web.data.entity.AdministratorsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorsRepository extends CrudRepository<AdministratorsEntity, String> {

}