package com.flood_web.service.crud;

import com.flood_web.controller.PersonRiskLog;
import com.flood_web.data.entity.PersonRiskLogEntity;
import com.flood_web.data.repository.PersonRiskLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service ("PersonRiskLogCrudService")
public class PersonRiskLogCrudService implements CrudService<PersonRiskLog>{

    @Autowired
    PersonRiskLogRepository personRiskLogRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(PersonRiskLog personRiskLog) {
        PersonRiskLogEntity personRiskLogEntity = modelMapper.map(personRiskLog, PersonRiskLogEntity.class);
        personRiskLogEntity.setDate(LocalDateTime.now());
        personRiskLogRepository.save(personRiskLogEntity);
    }

    @Override
    public List<PersonRiskLog> listAll() {
        List<PersonRiskLogEntity> entities = personRiskLogRepository.findAllByOrderByDateDesc();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, PersonRiskLog.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonRiskLog> findById(String idNumber) {
        return Optional.empty();
    }
}