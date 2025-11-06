package com.example.StandHealthMonitor.dto;

public class RsStatObj {
    private int status;
    private String system;

    public RsStatObj() {
    }

    public RsStatObj(int status, String system) {
        this.status = status;
        this.system = system;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
