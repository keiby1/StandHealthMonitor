package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;
import org.springframework.stereotype.Service;

@Service
public class SystemPingServiceImpl implements SystemPingService {

    @Override
    public PingResponse ping(String systemName) {
        Integer status = -1;
        try{
            Class<?> clazz = Class.forName("com.example.StandHealthMonitor.Systems."+systemName);

            Object obj = clazz.newInstance();

            if(!(obj instanceof PeriodicTask)){
                throw new RuntimeException("The given class does not inplemente the required interface.");
            }

            PeriodicTask myObj = (PeriodicTask) obj;

            return myObj.execute();
        }
        catch (Exception e){
            return new PingResponse(
                    systemName,
                    status,
                    "NOT_IMPLEMENTED",
                    "Метод execute() не реализован. Пожалуйста реализуйте его в SystemPingServiceImpl",
                    false
            );
        }
    }
}

