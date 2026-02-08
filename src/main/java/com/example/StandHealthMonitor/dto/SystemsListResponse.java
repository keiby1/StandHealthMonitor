package com.example.StandHealthMonitor.dto;

import java.util.List;

/**
 * DTO для ответа со списком систем.
 * systems — список систем с группами (name, group; group=null для систем без группы).
 */
public class SystemsListResponse {
    private List<SystemWithGroup> systems;

    public SystemsListResponse() {}

    public SystemsListResponse(List<SystemWithGroup> systems) {
        this.systems = systems;
    }

    public List<SystemWithGroup> getSystems() {
        return systems;
    }

    public void setSystems(List<SystemWithGroup> systems) {
        this.systems = systems;
    }
}

