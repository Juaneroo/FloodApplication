package com.flood_web.service.crud;

import com.flood_web.controller.Administrators;
import com.flood_web.data.entity.AdministratorsEntity;
import com.flood_web.data.repository.AdministratorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service ("administratorsCrudService")
public class AdministratorsCrudService implements CrudService<Administrators>{

    @Autowired
    AdministratorsRepository administratorsRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(Administrators administrators) {

        AdministratorsEntity administratorsEntity = modelMapper.map(administrators, AdministratorsEntity.class);
        administratorsRepository.save(administratorsEntity);
    }

    @Override
    public List<Administrators> listAll() {
        Iterable<AdministratorsEntity> entities = administratorsRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, Administrators.class)
                )
                .collect(Collectors.toList());

    }

    @Override
    public Optional<Administrators> findById(String idNumber) {

        Optional<AdministratorsEntity> foundAdministrators = this.administratorsRepository.findById(idNumber);
        if (foundAdministrators.isEmpty()){
            return Optional.of(Administrators.builder().build());
        }

        AdministratorsEntity administratorsEntity = foundAdministrators.get();
        Administrators administrators = modelMapper.map(administratorsEntity, Administrators.class);

        return Optional.of(administrators);
    }

}