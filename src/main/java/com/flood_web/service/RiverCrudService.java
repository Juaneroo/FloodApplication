package com.flood_web.service;

import com.flood_web.controller.River;
import com.flood_web.controller.Sensor;
import com.flood_web.data.entity.RiverEntity;
import com.flood_web.data.entity.SensorEntity;
import com.flood_web.data.repository.RiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RiverCrudService implements CrudService<River>{

    @Autowired
    RiverRepository riverRepository;


    @Override
    public void save(River obj) {

        RiverEntity river = RiverEntity.builder()
                .withId(UUID.randomUUID().toString().substring(0, 8))
                .withName(obj.name())
                .build();
        riverRepository.save(river);

    }

    @Override
    public List<River> listAll() {
        Iterable<RiverEntity> entities = riverRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> River.builder()
                        .withId(entity.getId())
                        .withName(entity.getName())
                        .build()
                )
                .collect(Collectors.toList());


    }
}
