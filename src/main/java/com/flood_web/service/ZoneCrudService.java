package com.flood_web.service;

import com.flood_web.controller.Zone;
import com.flood_web.data.entity.ZoneEntity;
import com.flood_web.data.repository.ZoneRepository;
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


    @Override
    public void save(Zone zone) {

        String idZone = null;
        if (zone.id() != null && !zone.id().trim().isEmpty()){
            idZone = zone.id();
        }else{
            idZone = UUID.randomUUID().toString().substring(0, 8);
        }

        ZoneEntity zoneEntity = ZoneEntity.builder()
                .withId(idZone)
                .withName(zone.name())
                .withActive(zone.active())
                .build();
        zoneRepository.save(zoneEntity);

    }

    @Override
    public List<Zone> listAll() {
        Iterable<ZoneEntity> entities = zoneRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> Zone.builder()
                        .withId(entity.getId())
                        .withName(entity.getName())
                        .withActive(entity.isActive())
                        .build()
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
        Zone zone = Zone.builder()
                .withId(zoneEntity.getId())
                .withName(zoneEntity.getName())
                .withActive(zoneEntity.isActive())
                .build();

        return Optional.of(zone);
    }
}