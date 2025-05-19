package com.flood_web.service.notification;

import com.flood_web.data.repository.NotificationRegistryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CleanupRegistryService {


    @Autowired
    private NotificationRegistryRepository notificationRegistryRepository;

    @Scheduled(fixedRate = 60000) // cada minuto
    public void eliminarRegistrosAntiguos() {
        LocalDateTime limite = LocalDateTime.now();
        int totalToBedeleted = notificationRegistryRepository.countRegistryBeforeGivenDate(limite);
        log.info("Eliminando registros de notificaciones antiguos de la base de datos. Total a eliminar: {}", totalToBedeleted);
        notificationRegistryRepository.deleteRegistryBeforeGivenDate(limite);
    }
}
