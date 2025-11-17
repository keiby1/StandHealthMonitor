package com.example.StandHealthMonitor.dto;

/**
 * DTO для ответа пинга системы.
 */
public class PingResponse {
    private String systemName;
    private Integer httpCode;
    private String statusCode;
    private String message;
    private boolean success;

    public PingResponse() {}

    public PingResponse(String systemName, Integer httpCode, String statusCode, String message, boolean success) {
        this.systemName = systemName;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

