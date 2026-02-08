package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;
import org.springframework.stereotype.Component;

/**
 * Пример реализации интерфейса PeriodicTask.
 * Создайте свои классы, реализующие интерфейс PeriodicTask,
 * и они автоматически будут выполняться раз в час.
 * 
 * Не забудьте добавить аннотацию @Component, чтобы Spring
 * автоматически зарегистрировал ваш класс.
 */
@Component
public class A1 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {
        String rqJson = TemplatesHolder.getTemplate("A1", "prep");
        System.out.println(rqJson);

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A1", "System A", 200, "0", "Все ок", true);
    }
}

