package com.flood_web.service.crud;

import com.flood_web.controller.Administrators;
import com.flood_web.data.entity.AdministratorsEntity;
import com.flood_web.data.repository.AdministratorsRepository;
import com.flood_web.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("administratorsCrudService")
public class AdministratorsCrudService implements CrudService<Administrators> {

    @Autowired
    private AdministratorsRepository administratorsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Administrators> findById(String idNumber) {
        Long id = Long.parseLong(idNumber); // Convertir el ID de String a Long
        Optional<AdministratorsEntity> foundAdministrators = this.administratorsRepository.findById(String.valueOf(id));

        if (foundAdministrators.isEmpty()) {
            return Optional.of(Administrators.builder().build());
        }

        AdministratorsEntity administratorsEntity = foundAdministrators.get();
        Administrators administrators = modelMapper.map(administratorsEntity, Administrators.class);

        return Optional.of(administrators);
    }

    @Override
    public List<Administrators> listAll() {
        Iterable<AdministratorsEntity> entities = administratorsRepository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, Administrators.class))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Administrators administrators) {
        AdministratorsEntity administratorsEntity = modelMapper.map(administrators, AdministratorsEntity.class);
        administratorsEntity.setPassword(passwordEncoder.encode(administrators.getPassword()));
        administratorsRepository.save(administratorsEntity);
    }

    public Optional<Administrators> getByCedula(String cedula) {
        Optional<AdministratorsEntity> foundAdministrators = this.administratorsRepository.findByCedula(cedula);

        if (foundAdministrators.isEmpty()) {
            return Optional.empty();
        }

        AdministratorsEntity administratorsEntity = foundAdministrators.get();
        Administrators administrators = modelMapper.map(administratorsEntity, Administrators.class);

        return Optional.ofNullable(administrators);
    }

}