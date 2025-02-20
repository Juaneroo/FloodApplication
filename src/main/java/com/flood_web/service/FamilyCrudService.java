package com.flood_web.service;

import com.flood_web.controller.Family;
import com.flood_web.data.entity.FamilyEntity;
import com.flood_web.data.repository.FamilyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FamilyCrudService implements CrudService<Family>{

    @Autowired
    FamilyRepository familyRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(Family family) {

        FamilyEntity familyEntity = modelMapper.map(family, FamilyEntity.class);
        familyRepository.save(familyEntity);

    }

    @Override
    public List<Family> listAll() {
        Iterable<FamilyEntity> entities = familyRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, Family.class)
                ).collect(Collectors.toList());


    }

    @Override
    public Optional<Family> findById(String id) {
        Optional<FamilyEntity> foundFamily = this.familyRepository.findById(id);
        if (foundFamily.isEmpty()){
            return Optional.of(Family.builder().build());
        }

        FamilyEntity familyEntity = foundFamily.get();
        Family family = modelMapper.map(familyEntity, Family.class);


        return Optional.of(family);
    }
}