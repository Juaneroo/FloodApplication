package com.flood_web.data.repository;

import com.flood_web.data.entity.NotificationRegistryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRegistryRepository extends CrudRepository<NotificationRegistryEntity, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM NotificationRegistryEntity n WHERE n.deleteAfter < :date")
    void deleteRegistryBeforeGivenDate(@Param("date") LocalDateTime date);

    @Query("SELECT COUNT(n) FROM NotificationRegistryEntity n WHERE n.deleteAfter < :date")
    int countRegistryBeforeGivenDate(@Param("date") LocalDateTime date);

}
