package com.example.StandHealthMonitor.Systems;

import com.example.StandHealthMonitor.dto.OperStatPbj;
import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.service.PeriodicTask;
import com.example.StandHealthMonitor.service.TemplatesHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
public class A11 implements PeriodicTask {
    String group = "G1";

    @Override
    public PingResponse execute() {

        Map<String, String> params = new HashMap<>();
        params.put("test", "123");
        params.put("qwe", "steeeep213");
        String rqJson = TemplatesHolder.getTemplate("A1", "prep", params);
        System.out.println(rqJson);

        List<OperStatPbj> list = new LinkedList<>();
        list.add(new OperStatPbj("Step1", "0", "200", TemplatesHolder.getTemplate("A2", "Step1"),
                "{\"rs\":\"sdfgdsf\", \"asdf\":\"asdfsdaf\"}", "http://localhost:8080/safsdf/13123213/4354352/fdg43yt/sdh3qy354yhf/dh3qyh3hfd/h3yhq35hdfghdvc"));
        list.add(new OperStatPbj("Step2", "0", "200",TemplatesHolder.getTemplate("A2", "Step2"),
                "{\"rs\":\"1111\", \"asdf\":\"2222\"}", "http://localhost:8080/v2/api/test"));

        System.out.println("Пример периодической задачи выполнен");
        return new PingResponse("A11", "System A", 200, "200", "Все ок", true, list);
    }
}

