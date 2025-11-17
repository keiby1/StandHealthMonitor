package com.example.StandHealthMonitor.dto;

import java.util.List;

/**
 * DTO для ответа со списком систем.
 */
public class SystemsListResponse {
    private List<String> systems;

    public SystemsListResponse() {}

    public SystemsListResponse(List<String> systems) {
        this.systems = systems;
    }

    public List<String> getSystems() {
        return systems;
    }

    public void setSystems(List<String> systems) {
        this.systems = systems;
    }
}

