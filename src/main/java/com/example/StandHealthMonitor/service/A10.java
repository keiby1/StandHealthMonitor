package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.OperStatPbj;
import com.example.StandHealthMonitor.dto.PingResponse;
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
public class A10 implements PeriodicTask {
    
    @Override
    public PingResponse execute() {

        String rq1 = "{\"Rq\":\"123\"}";
        String rs1 = "{\"Rs\":\"qwe\"}";
        String rq2 = "{\"Rs\":\"213543605860948796578\"}";
        String rs2 = "{\"Rs\":\"fksdalfbksabf\"}";


        List<OperStatPbj> list = new LinkedList<>();
        list.add(new OperStatPbj("Step1", "0", "200", rq1, rs1));
        list.add(new OperStatPbj("Step2", "-1", "428", rq2, rs2));

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("B1", 429, "-1", "RL Timeount", false, list);
    }
}

