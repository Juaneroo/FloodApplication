package com.flood_web.service;

import com.flood_web.controller.River;
import com.flood_web.data.entity.RiverEntity;
import com.flood_web.data.repository.RiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RiverCrudService implements CrudService<River>{

    @Autowired
    RiverRepository riverRepository;


    @Override
    public void save(River river) {

        String idRiver = null;
        if (river.id() != null && !river.id().trim().isEmpty()){
            idRiver = river.id();
        }else{
            idRiver = UUID.randomUUID().toString().substring(0, 8);
        }

        RiverEntity riverEntity = RiverEntity.builder()
                .withId(idRiver)
                .withName(river.name())
                .withActive(river.active())
                .build();
        riverRepository.save(riverEntity);

    }

    @Override
    public List<River> listAll() {
        Iterable<RiverEntity> entities = riverRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> River.builder()
                        .withId(entity.getId())
                        .withName(entity.getName())
                        .withActive(entity.isActive())
                        .build()
                )
                .collect(Collectors.toList());


    }

    @Override
    public Optional<River> findById(String id) {

        Optional<RiverEntity> foundRiver = this.riverRepository.findById(id);
        if (foundRiver.isEmpty()){
            return Optional.of(River.builder().build());
        }

        RiverEntity riverEntity = foundRiver.get();
        River river = River.builder()
                .withId(riverEntity.getId())
                .withName(riverEntity.getName())
                .withActive(riverEntity.isActive())
                .build();

        return Optional.of(river);
    }
}
