package com.flood_web.service.crud;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.controller.PersonRiskLog;
import com.flood_web.data.entity.FamilyMemberEntity;
import com.flood_web.data.entity.PersonRiskLogEntity;
import com.flood_web.data.repository.FamilyMembersRepository;
import com.flood_web.data.repository.PersonRiskLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service ("personRiskLogCrudService")
public class PersonRiskLogCrudService implements CrudService<PersonRiskLog>{

    @Autowired
    PersonRiskLogRepository personRiskLogRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void save(PersonRiskLog personRiskLog) {
        PersonRiskLogEntity familyMemberEntity = modelMapper.map(personRiskLog, PersonRiskLogEntity.class);
        familyMemberEntity.setDate(LocalDateTime.now());
        personRiskLogRepository.save(familyMemberEntity);
    }

    @Override
    public List<PersonRiskLog> listAll() {
        Iterable<PersonRiskLogEntity> entities = personRiskLogRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, PersonRiskLog.class)
                )
                .collect(Collectors.toList());

    }

    @Override
    public Optional<PersonRiskLog> findById(String idNumber) {

        return Optional.empty();
    }
}