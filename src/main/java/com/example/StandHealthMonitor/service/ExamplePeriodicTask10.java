package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.dto.RsStatObj;
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
public class ExamplePeriodicTask10 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("B1", 429, "-1", "RL Timeount", false);
    }
}

