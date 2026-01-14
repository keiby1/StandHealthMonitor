package com.example.StandHealthMonitor.dto;

public class OperStatPbj {
    String step;
    String status;
    String httpCode;
    String requestBody;
    String responseBody;

    public OperStatPbj() {
    }

    public OperStatPbj(String step, String status, String httpCode) {
        this.step = step;
        this.status = status;
        this.httpCode = httpCode;
    }

    public OperStatPbj(String step, String status, String httpCode, String requestBody, String responseBody) {
        this.step = step;
        this.status = status;
        this.httpCode = httpCode;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
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

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "OperStatPbj{" +
                "step='" + step + '\'' +
                ", status='" + status + '\'' +
                ", httpCode='" + httpCode + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
