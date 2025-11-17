package com.example.StandHealthMonitor.controller;

import com.example.StandHealthMonitor.dto.PingRequest;
import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.dto.SystemsListResponse;
import com.example.StandHealthMonitor.repository.SystemStatusRepository;
import com.example.StandHealthMonitor.service.SystemPingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки запросов пинга систем.
 */
@RestController
@RequestMapping("/api/ping")
public class PingController {
    
    private final SystemPingService systemPingService;
    private final SystemStatusRepository systemStatusRepository;

    public PingController(SystemPingService systemPingService, SystemStatusRepository systemStatusRepository) {
        this.systemPingService = systemPingService;
        this.systemStatusRepository = systemStatusRepository;
    }

    /**
     * Получает список всех доступных систем для пинга.
     * Список формируется из уникальных названий систем в таблице system_statuses.
     * 
     * @return список названий систем
     */
    @GetMapping("/systems")
    public ResponseEntity<SystemsListResponse> getSystems() {
        // Получаем все уникальные названия систем из базы данных
        Set<String> uniqueSystems = systemStatusRepository.findAll().stream()
                .map(status -> status.getSystemName())
                .collect(Collectors.toSet());
        
        List<String> systemsList = new ArrayList<>(uniqueSystems);
        systemsList.sort(String::compareTo); // Сортируем по алфавиту
        
        return ResponseEntity.ok(new SystemsListResponse(systemsList));
    }

    /**
     * Выполняет пинг указанной системы.
     * 
     * @param request запрос с названием системы
     * @return результат пинга с HTTP кодом и статус кодом операции
     */
    @PostMapping("/execute")
    public ResponseEntity<PingResponse> pingSystem(@RequestBody PingRequest request) {
        if (request == null || request.getSystemName() == null || request.getSystemName().trim().isEmpty()) {
            PingResponse errorResponse = new PingResponse(
                null, 
                HttpStatus.BAD_REQUEST.value(), 
                "ERROR", 
                "Название системы не указано", 
                false
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String systemName = request.getSystemName().trim();
        
        // Проверяем, что система существует в базе данных
        Set<String> availableSystems = systemStatusRepository.findAll().stream()
                .map(status -> status.getSystemName())
                .collect(Collectors.toSet());
        
        if (!availableSystems.contains(systemName)) {
            PingResponse errorResponse = new PingResponse(
                systemName, 
                HttpStatus.NOT_FOUND.value(), 
                "ERROR", 
                "Система не найдена в списке доступных систем", 
                false
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        try {
            PingResponse response = systemPingService.ping(systemName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            PingResponse errorResponse = new PingResponse(
                systemName, 
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "ERROR", 
                "Ошибка при выполнении пинга: " + e.getMessage(), 
                false
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}

