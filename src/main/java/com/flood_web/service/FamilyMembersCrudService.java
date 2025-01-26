package com.flood_web.service;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.data.entity.FamilyMembersEntity;
import com.flood_web.data.repository.FamilyMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service ("familyMembersCrudService")
public class FamilyMembersCrudService implements CrudService<FamilyMembers>{

    @Autowired
    FamilyMembersRepository familyMembersRepository;


    @Override
    public void save(FamilyMembers familyMembers) {

        String idFamilyMembers = null;
        if (familyMembers.id() != null && !familyMembers.id().trim().isEmpty()){
            idFamilyMembers = familyMembers.id();
        }else{
            idFamilyMembers = UUID.randomUUID().toString().substring(0, 8);
        }

        FamilyMembersEntity familyMembersEntity = FamilyMembersEntity.builder()
                .withId(idFamilyMembers)
                .withName(familyMembers.name())
                .withActive(familyMembers.active())
                .withIdNumber(familyMembers.idNumber())
                .withTelephone(familyMembers.telephone())
                .build();
        familyMembersRepository.save(familyMembersEntity);

    }

    @Override
    public List<FamilyMembers> listAll() {
        Iterable<FamilyMembersEntity> entities = familyMembersRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> FamilyMembers.builder()
                        .withId(entity.getId())
                        .withName(entity.getName())
                        .withActive(entity.isActive())
                        .withIdNumber(entity.getIdNumber())
                        .withTelephone(entity.getTelephone())
                        .build()
                )
                .collect(Collectors.toList());

    }

    @Override
    public Optional<FamilyMembers> findById(String id) {
        Optional<FamilyMembersEntity> foundFamilyMembers = this.familyMembersRepository.findById(id);
        if (foundFamilyMembers.isEmpty()){
            return Optional.of(FamilyMembers.builder().build());
        }

        FamilyMembersEntity familyMembersEntity = foundFamilyMembers.get();
        FamilyMembers familyMembers = FamilyMembers.builder()
                .withId(familyMembersEntity.getId())
                .withName(familyMembersEntity.getName())
                .withActive(familyMembersEntity.isActive())
                .withIdNumber(familyMembersEntity.getIdNumber())
                .withTelephone(familyMembersEntity.getTelephone())
                .build();

        return Optional.of(familyMembers);
    }
}