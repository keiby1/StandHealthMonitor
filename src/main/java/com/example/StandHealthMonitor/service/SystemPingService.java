package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;

/**
 * Интерфейс для выполнения пинга систем.
 * Реализуйте этот интерфейс в своем классе для выполнения проверки работоспособности системы.
 */
public interface SystemPingService {
    /**
     * Выполняет пинг указанной системы.
     * 
     * @param systemName название системы для проверки
     * @return результат пинга с HTTP кодом и статус кодом операции из response body
     */
    PingResponse ping(String systemName);
}

