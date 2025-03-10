package com.flood_web.service;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.controller.Zone;
import com.flood_web.data.entity.FamilyMembersEntity;
import com.flood_web.data.entity.ZoneEntity;
import com.flood_web.data.repository.FamilyMembersRepository;
import com.flood_web.data.repository.ZoneRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(FamilyMembers familyMembers) {

        FamilyMembersEntity familyMembersEntity = modelMapper.map(familyMembers, FamilyMembersEntity.class);
        familyMembersRepository.save(familyMembersEntity);
    }

    @Override
    public List<FamilyMembers> listAll() {
        Iterable<FamilyMembersEntity> entities = familyMembersRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, FamilyMembers.class)
                )
                .collect(Collectors.toList());

    }

    @Override
    public Optional<FamilyMembers> findById(String idNumber) {

        Optional<FamilyMembersEntity> foundFamilyMembers = this.familyMembersRepository.findById(idNumber);
        if (foundFamilyMembers.isEmpty()){
            return Optional.of(FamilyMembers.builder().build());
        }

        FamilyMembersEntity familyMembersEntity = foundFamilyMembers.get();
        FamilyMembers familyMembers = modelMapper.map(familyMembersEntity, FamilyMembers.class);

        return Optional.of(familyMembers);
    }
}