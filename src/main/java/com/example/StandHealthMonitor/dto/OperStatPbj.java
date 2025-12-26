package com.example.StandHealthMonitor.dto;

public class OperStatPbj {
    String step;
    String status;
    String httpCode;

    public OperStatPbj() {
    }

    public OperStatPbj(String step, String status, String httpCode) {
        this.step = step;
        this.status = status;
        this.httpCode = httpCode;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "OperStatPbj{" +
                "step='" + step + '\'' +
                ", status='" + status + '\'' +
                ", httpCode='" + httpCode + '\'' +
                '}';
    }
}
