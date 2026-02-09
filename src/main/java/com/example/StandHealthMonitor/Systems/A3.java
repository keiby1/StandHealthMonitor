package com.example.StandHealthMonitor.Systems;

import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.service.PeriodicTask;
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
public class A3 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {
        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A3", 500, "-4", ":(", false);
    }
}

