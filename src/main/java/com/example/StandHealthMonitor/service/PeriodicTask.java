package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.RsStatObj;

/**
 * Интерфейс для периодических задач.
 * Реализуйте этот интерфейс в ваших классах, чтобы они автоматически выполнялись раз в час.
 */
public interface PeriodicTask {
    /**
     * Метод, который будет вызываться периодически.
     * Реализуйте здесь свою логику.
     */
    RsStatObj execute();
}

