package com.flood_web.service;

import com.flood_web.controller.Zone;
import com.flood_web.data.entity.ZoneEntity;
import com.flood_web.data.repository.ZoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("zoneCrudService")
public class ZoneCrudService implements CrudService<Zone>{

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(Zone zone) {

        ZoneEntity zoneEntity = modelMapper.map(zone, ZoneEntity.class);
        zoneRepository.save(zoneEntity);
    }

    @Override
    public List<Zone> listAll() {
        Iterable<ZoneEntity> entities = zoneRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, Zone.class)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Zone> findById(String id) {

        Optional<ZoneEntity> foundZone = this.zoneRepository.findById(id);
        if (foundZone.isEmpty()){
            return Optional.of(Zone.builder().build());
        }

        ZoneEntity zoneEntity = foundZone.get();
        Zone zone = modelMapper.map(zoneEntity, Zone.class);

        return Optional.of(zone);
    }
}