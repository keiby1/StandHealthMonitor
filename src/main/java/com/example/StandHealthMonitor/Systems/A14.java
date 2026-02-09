package com.example.StandHealthMonitor.Systems;

import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.service.PeriodicTask;
import com.example.StandHealthMonitor.service.TemplatesHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
public class A14 implements PeriodicTask {
    String group = "G1";

    @Override
    public PingResponse execute() {
        Map<String, String> params = new HashMap<>();
        params.put("test", "123");
        params.put("qwe", "steeeep213");
        String rqJson = TemplatesHolder.getTemplate("A1", "prep", params);
        System.out.println(rqJson);

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A14", "System A", 200, "200", "Все ок", true);
    }
}

