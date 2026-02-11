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

    /** Код статуса из PingResponse.statusCode */
    @Column(nullable = false, length = 50)
    private String status;

    @Column(name = "system_name", nullable = false, length = 100)
    private String systemName;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer count;

    /** HTTP-код из PingResponse.httpCode */
    @Column(name = "http_code")
    private Integer httpCode;

    /** Флаг успешности операции из PingResponse.success */
    @Column(name = "success")
    private Boolean success;

    public SystemStatus() {}

    public SystemStatus(String status, String systemName, LocalDate date, Integer count) {
        this.status = status;
        this.systemName = systemName;
        this.date = date;
        this.count = count;
    }

    public SystemStatus(String status, String systemName, LocalDate date, Integer count, Integer httpCode, Boolean success) {
        this.status = status;
        this.systemName = systemName;
        this.date = date;
        this.count = count;
        this.httpCode = httpCode;
        this.success = success;
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

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

