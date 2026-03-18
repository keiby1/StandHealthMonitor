package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;

/**
 * Интерфейс для периодических задач.
 * Реализуйте этот интерфейс в ваших классах, чтобы они автоматически выполнялись раз в час.
 * Пороги времени (зелёный/красный в UI) задаются статическими полями в классе системы:
 * GOOD_THRESHOLD_MS и BAD_THRESHOLD_MS (long). Если не заданы — используются значения по умолчанию.
 */
public interface PeriodicTask {
    /**
     * Метод, который будет вызываться периодически.
     * Реализуйте здесь свою логику.
     */
    PingResponse execute();
}

