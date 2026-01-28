package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.OperStatPbj;
import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.dto.RsStatObj;
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
public class ExamplePeriodicTask2 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {
        System.out.println("Пример периодической задачи выполнен");

        List<OperStatPbj> list = new LinkedList<>();
        list.add(new OperStatPbj("Step1", "0", "200", "{ \"rq\":\"123\", \"dsa\":\"sdfsdf\"}",
                "{\"rs\":\"sdfgdsf\", \"asdf\":\"asdfsdaf\"}", "http://localhost:8080"));
        list.add(new OperStatPbj("Step2", "0", "200","{ \"rq\":\"333\", \"ds424a\":\"3333\"}",
                "{\"rs\":\"1111\", \"asdf\":\"2222\"}", "http://localhost:8080/v2/api/test"));

        return new PingResponse("A2", 200, "-1", "sadewqesad", true, list);
    }
}

