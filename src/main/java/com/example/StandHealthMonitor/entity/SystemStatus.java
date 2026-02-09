package com.example.StandHealthMonitor.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "system_statuses", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"date", "system_name", "status"}))
public class SystemStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "system_name", nullable = false, length = 100)
    private String systemName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer count;

    public SystemStatus() {}

    public SystemStatus(String status, String systemName, LocalDate date, Integer count) {
        this.status = status;
        this.systemName = systemName;
        this.date = date;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

