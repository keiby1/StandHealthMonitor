package com.example.StandHealthMonitor.dto;

import java.util.List;

/**
 * DTO для ответа пинга системы.
 */
public class PingResponse {
    private String systemName;
    private String fullSystemName;
    private Integer httpCode;
    private String statusCode;
    private String message;
    private boolean success;
    private List<OperStatPbj> steps;

    public PingResponse() {}

    public PingResponse(String systemName, Integer httpCode, String statusCode, String message, boolean success) {
        this.systemName = systemName;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
    }

    public PingResponse(String systemName, String fullSystemName, Integer httpCode, String statusCode, String message, boolean success) {
        this.systemName = systemName;
        this.fullSystemName = fullSystemName;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
    }

    public PingResponse(String systemName, Integer httpCode, String statusCode, String message, boolean success, List<OperStatPbj> steps) {
        this.systemName = systemName;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.steps = steps;
    }

    public PingResponse(String systemName, String fullSystemName, Integer httpCode, String statusCode, String message, boolean success, List<OperStatPbj> steps) {
        this.systemName = systemName;
        this.fullSystemName = fullSystemName;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.steps = steps;
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

    public String getFullSystemName() {
        return fullSystemName;
    }

    public void setFullSystemName(String fullSystemName) {
        this.fullSystemName = fullSystemName;
    }

    public List<OperStatPbj> getSteps() {
        return steps;
    }

    public void setSteps(List<OperStatPbj> steps) {
        this.steps = steps;
    }

    public void add(OperStatPbj step){
        this.steps.add(step);
    }
}

