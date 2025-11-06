package com.example.StandHealthMonitor.controller;

import com.example.StandHealthMonitor.dto.CriteriaResponse;
import com.example.StandHealthMonitor.dto.DataResponse;
import com.example.StandHealthMonitor.entity.Criteria;
import com.example.StandHealthMonitor.entity.SystemStatus;
import com.example.StandHealthMonitor.repository.CriteriaRepository;
import com.example.StandHealthMonitor.repository.SystemStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DataController {
    private final CriteriaRepository criteriaRepository;
    private final SystemStatusRepository systemStatusRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public DataController(CriteriaRepository criteriaRepository, SystemStatusRepository systemStatusRepository) {
        this.criteriaRepository = criteriaRepository;
        this.systemStatusRepository = systemStatusRepository;
    }

    /**
     * Получает критерии из таблицы criteria в БД.
     * Данные берутся только из PostgreSQL, файлы не используются.
     */
    @GetMapping("/criteria")
    public ResponseEntity<CriteriaResponse> getCriteria() {
        List<Criteria> criteriaList = criteriaRepository.findAllByOrderByCountAsc();
        List<CriteriaResponse.CriteriaItem> items = criteriaList.stream()
                .map(c -> new CriteriaResponse.CriteriaItem(c.getCount(), c.getColor()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CriteriaResponse(items));
    }

    /**
     * Получает данные о статусах систем из таблицы system_statuses в БД.
     * Данные берутся только из PostgreSQL, файлы не используются.
     * Группирует статусы по дате и передает count для компактного отображения.
     */
    @GetMapping("/data")
    public ResponseEntity<DataResponse> getData() {
        // Получаем все статусы из таблицы system_statuses в БД
        List<SystemStatus> allStatuses = systemStatusRepository.findAllOrderByDateDesc();
        
        // Группируем по дате
        Map<LocalDate, List<SystemStatus>> statusesByDate = allStatuses.stream()
                .collect(Collectors.groupingBy(SystemStatus::getDate));
        
        // Преобразуем в формат для фронтенда с count
        List<DataResponse.DayData> dayDataList = statusesByDate.entrySet().stream()
                .sorted(Map.Entry.<LocalDate, List<SystemStatus>>comparingByKey().reversed())
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    String dateStr = date.format(DATE_FORMATTER);
                    
                    // Передаем записи с count вместо размножения
                    List<DataResponse.SystemStat> stats = entry.getValue().stream()
                            .map(status -> new DataResponse.SystemStat(
                                    status.getSystemName(), 
                                    status.getStatus(), 
                                    status.getCount()))
                            .collect(Collectors.toList());
                    
                    return new DataResponse.DayData(dateStr, stats);
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(new DataResponse(dayDataList));
    }
}

