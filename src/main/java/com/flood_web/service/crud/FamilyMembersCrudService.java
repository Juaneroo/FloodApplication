package com.flood_web.service.crud;

import com.flood_web.controller.FamilyMembers;
import com.flood_web.data.entity.FamilyMemberEntity;
import com.flood_web.data.repository.FamilyMembersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service ("familyMembersCrudService")
public class FamilyMembersCrudService implements CrudService<FamilyMembers>{

    @Autowired
    FamilyMembersRepository familyMembersRepository;

    @Autowired
    ModelMapper modelMapper;

    // Método para verificar la existencia por cedula
    public boolean existsByCedula(String cedula) {
        return familyMembersRepository.existsByCedula(cedula);
    }

    @Override
    public void save(FamilyMembers familyMembers) {
        FamilyMemberEntity familyMemberEntity = modelMapper.map(familyMembers, FamilyMemberEntity.class);
        familyMembersRepository.save(familyMemberEntity);
    }

    @Override
    public List<FamilyMembers> listAll() {
        Iterable<FamilyMemberEntity> entities = familyMembersRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> modelMapper.map(entity, FamilyMembers.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FamilyMembers> findById(String idNumber) {
        // Este método sigue buscando por la clave primaria (que ahora es Long)
        // Si necesitas buscar por cedula para otras funcionalidades, usa findByCedula
        Optional<FamilyMemberEntity> foundFamilyMembers = familyMembersRepository.findById(idNumber);
        if (foundFamilyMembers.isEmpty()){
            return Optional.of(FamilyMembers.builder().build());
        }
        FamilyMemberEntity familyMemberEntity = foundFamilyMembers.get();
        FamilyMembers familyMembers = modelMapper.map(familyMemberEntity, FamilyMembers.class);
        return Optional.of(familyMembers);
    }

    public Set<FamilyMembers> findPeopleUnderRisk(String sensorUnderRisk){
        List<FamilyMemberEntity> peopleUnderRisk = familyMembersRepository.findBySensorIdAndActive(sensorUnderRisk);
        return peopleUnderRisk
                .stream()
                .map(member -> modelMapper.map(member, FamilyMembers.class))
                .collect(Collectors.toSet());
    }

    public String findAssociatedZoneName(String familyMemberId) {
        FamilyMemberEntity familyMemberEntity = familyMembersRepository.findById(familyMemberId).orElse(null);
        if (familyMemberEntity == null) {
            return "Zona no encontrada"; // Zone not found
        }
        return familyMemberEntity.getFamily().getZone().getName();
    }
}