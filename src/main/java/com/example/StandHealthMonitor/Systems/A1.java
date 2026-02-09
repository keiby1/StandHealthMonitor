package com.example.StandHealthMonitor.Systems;

import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.service.PeriodicTask;
import com.example.StandHealthMonitor.service.TemplatesHolder;
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
public class A1 implements PeriodicTask {

    String group = "Пример";

    @Override
    public PingResponse execute() {
        String rqJson = TemplatesHolder.getTemplate("A1", "prep",
                Map.of(
                        "test", "123",
                        "qwe", "steeeep213"));
        System.out.println(rqJson);

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A1", "System A", 200, "200", "Все ок", true);
    }
}

