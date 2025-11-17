package com.example.StandHealthMonitor.dto;

/**
 * DTO для запроса пинга системы.
 */
public class PingRequest {
    private String systemName;

    public PingRequest() {}

    public PingRequest(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}

