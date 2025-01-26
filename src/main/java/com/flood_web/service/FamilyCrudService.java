package com.flood_web.service;

import com.flood_web.controller.Family;
import com.flood_web.data.entity.FamilyEntity;
import com.flood_web.data.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service ("familyCrudService")
public class FamilyCrudService implements CrudService<Family>{

    @Autowired
    FamilyRepository familyRepository;


    @Override
    public void save(Family family) {

        String idFamily = null;
        if (family.id() != null && !family.id().trim().isEmpty()){
            idFamily = family.id();
        }else{
            idFamily = UUID.randomUUID().toString().substring(0, 8);
        }

        FamilyEntity familyEntity = FamilyEntity.builder()
                .withId(idFamily)
                .withName(family.name())
                .withActive(family.active())
                .build();
        familyRepository.save(familyEntity);

    }

    @Override
    public List<Family> listAll() {
        Iterable<FamilyEntity> entities = familyRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> Family.builder()
                        .withId(entity.getId())
                        .withName(entity.getName())
                        .withActive(entity.isActive())
                        .build()
                )
                .collect(Collectors.toList());


    }

    @Override
    public Optional<Family> findById(String id) {
        Optional<FamilyEntity> foundFamily = this.familyRepository.findById(id);
        if (foundFamily.isEmpty()){
            return Optional.of(Family.builder().build());
        }

        FamilyEntity familyEntity = foundFamily.get();
        Family family = Family.builder()
                .withId(familyEntity.getId())
                .withName(familyEntity.getName())
                .withActive(familyEntity.isActive())
                .build();

        return Optional.of(family);
    }
}