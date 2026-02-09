package com.example.StandHealthMonitor.service;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реестр групп систем.
 * Считывает поле {@code group} из классов PeriodicTask при помощи рефлексии.
 */
@Component
public class SystemGroupRegistry {

    private final Map<String, String> systemToGroup = new HashMap<>();

    public SystemGroupRegistry(List<PeriodicTask> tasks) {
        for (PeriodicTask task : tasks) {
            String systemName = task.getClass().getSimpleName();
            String group = readGroupField(task);
            systemToGroup.put(systemName, (group == null || group.trim().isEmpty()) ? null : group);
        }
    }

    private static String readGroupField(PeriodicTask task) {
        Class<?> clazz = task.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField("group");
                if (field.getType() != String.class) return null;
                field.setAccessible(true);
                Object value = field.get(task);
                return value != null ? (String) value : null;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Возвращает группу для системы, или null если группа не задана.
     */
    public String getGroup(String systemName) {
        return systemToGroup.get(systemName);
    }
}
