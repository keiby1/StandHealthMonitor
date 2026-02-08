package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Пример реализации интерфейса PeriodicTask.
 * Создайте свои классы, реализующие интерфейс PeriodicTask,
 * и они автоматически будут выполняться раз в час.
 * <p>
 * Не забудьте добавить аннотацию @Component, чтобы Spring
 * автоматически зарегистрировал ваш класс.
 */
@Component
public class A17 implements PeriodicTask {

    @Override
    public PingResponse execute() {
        String rqJson = TemplatesHolder.getTemplate("A1", "prep",
                Map.of(
                        "test", "123",
                        "qwe", "steeeep213"));
        System.out.println(rqJson);

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A17", "System A", 200, "200", "Все ок", true);
    }
}

