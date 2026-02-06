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
public class A6 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {
        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A6", 200,  "NOT_IMPLEMENTED", "Метод ping() не реализован. Пожалуйста, реализуйте его в SystemPingServiceImpl", false);
    }
}

