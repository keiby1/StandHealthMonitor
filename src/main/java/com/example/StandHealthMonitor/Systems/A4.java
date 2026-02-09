package com.example.StandHealthMonitor.Systems;

import com.example.StandHealthMonitor.dto.OperStatPbj;
import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.service.PeriodicTask;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Пример реализации интерфейса PeriodicTask.
 * Создайте свои классы, реализующие интерфейс PeriodicTask,
 * и они автоматически будут выполняться раз в час.
 * 
 * Не забудьте добавить аннотацию @Component, чтобы Spring
 * автоматически зарегистрировал ваш класс.
 */
@Component
public class A4 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {
        System.out.println("Пример периодической задачи выполнен");

        List<OperStatPbj> list = new LinkedList<>();
        list.add(new OperStatPbj("Step1", "0", "200"));
        list.add(new OperStatPbj("Step2", "0", "200"));

        return new PingResponse("A4", 200, "0", "Все ок", true, list);
    }
}

