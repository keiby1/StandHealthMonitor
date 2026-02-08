package com.example.StandHealthMonitor.dto;

/**
 * DTO для системы с указанием группы.
 */
public class SystemWithGroup {
    private final String name;
    private final String group;

    public SystemWithGroup(String name, String group) {
        this.name = name;
        this.group = (group == null || group.isBlank()) ? null : group;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
}
