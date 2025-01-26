package com.flood_web.data.repository;

import com.flood_web.data.entity.FamilyMembersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyMembersRepository extends CrudRepository<FamilyMembersEntity, String> {

}