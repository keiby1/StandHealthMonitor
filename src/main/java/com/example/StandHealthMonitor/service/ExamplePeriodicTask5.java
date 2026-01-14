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
public class ExamplePeriodicTask5 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {

        String rq1 = "{\"Rq\":\"123\"}";
        String rs1 = "{\"Rs\":\"qwe\"}";
        String rq2 = "{\"Rs\":\"213543605860948796578\"}";
        String rs2 = "{\"Rs\":\"fksdalfbksabf\"}";


        List<OperStatPbj> list = new LinkedList<>();
        list.add(new OperStatPbj("Step1", "0", "200", rq1, rs1));
        list.add(new OperStatPbj("Step2", "0", "200", rq2, rs2));

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A5", 200, "0", "Все ок", true, list);
    }
}

