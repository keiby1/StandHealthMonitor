package com.example.StandHealthMonitor.service;

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
public class ExamplePeriodicTask9 implements PeriodicTask {
    
    @Override
    public RsStatObj execute() {
        // Реализуйте здесь свою логику
        System.out.println("Пример периодической задачи выполнен");
        return new RsStatObj(200, "A10");
    }
}

