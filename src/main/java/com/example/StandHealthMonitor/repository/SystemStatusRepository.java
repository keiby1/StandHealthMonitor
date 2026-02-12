package com.example.StandHealthMonitor.repository;

import com.example.StandHealthMonitor.entity.SystemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SystemStatusRepository extends JpaRepository<SystemStatus, Long> {
    List<SystemStatus> findByDateOrderByDateDesc(LocalDate date);
    
    @Query("SELECT s FROM SystemStatus s ORDER BY s.date DESC")
    List<SystemStatus> findAllOrderByDateDesc();
    
    /**
     * Находит запись по дате, названию системы и статусу.
     * Используется для проверки существования записи перед обновлением счетчика.
     */
    Optional<SystemStatus> findByDateAndSystemNameAndStatus(LocalDate date, String systemName, String status);
    
    /**
     * Находит запись по дате, названию системы, статусу и HTTP-коду.
     * Используется для проверки существования записи перед обновлением счетчика.
     */
    Optional<SystemStatus> findByDateAndSystemNameAndStatusAndHttpCode(LocalDate date, String systemName, String status, Integer httpCode);
}

