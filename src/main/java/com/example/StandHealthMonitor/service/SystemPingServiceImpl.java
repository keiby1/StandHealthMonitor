package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Service
public class SystemPingServiceImpl implements SystemPingService {

    /** Значения по умолчанию для порогов (мс), если в классе системы не заданы константы. */
    private static final long DEFAULT_GOOD_THRESHOLD_MS = 1000;
    private static final long DEFAULT_BAD_THRESHOLD_MS = 10000;

    private static final String FIELD_GOOD = "GOOD_THRESHOLD_MS";
    private static final String FIELD_BAD = "BAD_THRESHOLD_MS";

    /**
     * Читает статическое поле long/Long с именем fieldName из класса задачи.
     * Поддерживает private static final. Если поле не найдено или не число — возвращает defaultValue.
     */
    private static long getThresholdFromClass(Class<?> taskClass, String fieldName, long defaultValue) {
        try {
            Field field = taskClass.getDeclaredField(fieldName);
            if (!Modifier.isStatic(field.getModifiers())) {
                return defaultValue;
            }
            field.setAccessible(true);
            Object value = field.get(null);
            if (value instanceof Long) {
                return ((Long) value).longValue();
            }
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            // поле не задано — используем значение по умолчанию
        }
        return defaultValue;
    }

    @Override
    public PingResponse ping(String systemName) {
        Integer status = -1;
        try {
            Class<?> clazz = Class.forName("com.example.StandHealthMonitor.Systems." + systemName);

            Object obj = clazz.getDeclaredConstructor().newInstance();

            if (!(obj instanceof PeriodicTask)) {
                throw new RuntimeException("The given class does not inplemente the required interface.");
            }

            PeriodicTask myObj = (PeriodicTask) obj;

            long startMs = System.currentTimeMillis();
            PingResponse response = myObj.execute();
            long executionTimeMs = System.currentTimeMillis() - startMs;

            response.setExecutionTimeMs(executionTimeMs);
            response.setGoodThresholdMs(getThresholdFromClass(clazz, FIELD_GOOD, DEFAULT_GOOD_THRESHOLD_MS));
            response.setBadThresholdMs(getThresholdFromClass(clazz, FIELD_BAD, DEFAULT_BAD_THRESHOLD_MS));
            return response;
        } catch (Exception e) {
            return new PingResponse(
                    systemName,
                    status,
                    "NOT_IMPLEMENTED",
                    "Метод execute() не реализован. Пожалуйста реализуйте его в SystemPingServiceImpl",
                    false
            );
        }
    }
}

