package com.flood_web.service.crud;

import com.flood_web.controller.River;
import com.flood_web.data.entity.RiverEntity;
import com.flood_web.data.repository.RiverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RiverCrudService implements CrudService<River>{

    @Autowired
    RiverRepository riverRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(River river) {

        RiverEntity riverEntity = modelMapper.map(river, RiverEntity.class);
        riverRepository.save(riverEntity);
    }

    @Override
    public List<River> listAll() {
        Iterable<RiverEntity> entities = riverRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, River.class)
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
        River river = modelMapper.map(riverEntity, River.class);

        return Optional.of(river);
    }
}
