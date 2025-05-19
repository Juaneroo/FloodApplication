package com.flood_web.service.crud;

import com.flood_web.controller.PersonRiskLog;
import com.flood_web.data.entity.PersonRiskLogEntity;
import com.flood_web.data.repository.PersonRiskLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("PersonRiskLogCrudService")
public class PersonRiskLogCrudService implements CrudService<PersonRiskLog> {

    @Autowired
    PersonRiskLogRepository personRiskLogRepository;

    @Autowired
    ModelMapper modelMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void save(PersonRiskLog personRiskLog) {
        // implementación save
    }

    @Override
    public List<PersonRiskLog> listAll() {
        List<PersonRiskLogEntity> entities = personRiskLogRepository.findAllByOrderByDateDesc();
        return entities.stream()
                .map(entity -> {
                    PersonRiskLog dto = modelMapper.map(entity, PersonRiskLog.class);
                    if (entity.getDate() != null) {
                        dto.setDateFormatted(entity.getDate().format(FORMATTER));
                    } else {
                        dto.setDateFormatted("Sin fecha");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<PersonRiskLog> findBetweenDates(String desdeStr, String hastaStr) {
        // Parseamos las fechas y ajustamos para incluir todo el día
        LocalDateTime desde = LocalDateTime.of(LocalDateTime.parse(desdeStr + "T00:00:00").toLocalDate(), LocalTime.MIN);
        LocalDateTime hasta = LocalDateTime.of(LocalDateTime.parse(hastaStr + "T00:00:00").toLocalDate(), LocalTime.MAX);

        List<PersonRiskLogEntity> entities = personRiskLogRepository.findByDateBetweenOrderByDateDesc(desde, hasta);

        return entities.stream()
                .map(entity -> {
                    PersonRiskLog dto = modelMapper.map(entity, PersonRiskLog.class);
                    if (entity.getDate() != null) {
                        dto.setDateFormatted(entity.getDate().format(FORMATTER));
                    } else {
                        dto.setDateFormatted("Sin fecha");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonRiskLog> findById(String idNumber) {
        return Optional.empty();
    }
}
