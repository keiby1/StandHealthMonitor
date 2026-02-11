package com.example.StandHealthMonitor.service;

import com.example.StandHealthMonitor.dto.PingResponse;
import com.example.StandHealthMonitor.dto.RsStatObj;
import com.example.StandHealthMonitor.entity.SystemStatus;
import com.example.StandHealthMonitor.repository.SystemStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PeriodicTaskService {
    private ScheduledExecutorService scheduler;
    
    @Autowired(required = false)
    private List<PeriodicTask> periodicTasks;
    
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SystemStatusRepository systemStatusRepository;

    @PostConstruct
    public void init() {
        // Дополнительная проверка через ApplicationContext для отладки
        Map<String, PeriodicTask> taskBeans = applicationContext.getBeansOfType(PeriodicTask.class);
        System.out.println("=== Отладка: Поиск задач через ApplicationContext ===");
        System.out.println("Найдено бинов типа PeriodicTask: " + taskBeans.size());
        for (String beanName : taskBeans.keySet()) {
            System.out.println("  Бин: " + beanName + " -> " + taskBeans.get(beanName).getClass().getSimpleName());
        }
        System.out.println("===================================================");
        
        // Логируем зарегистрированные задачи при старте
        if (periodicTasks != null && !periodicTasks.isEmpty()) {
            System.out.println("=== Зарегистрированные периодические задачи ===");
            for (PeriodicTask task : periodicTasks) {
                System.out.println("  - " + task.getClass().getSimpleName() + 
                                 " (полное имя: " + task.getClass().getName() + ")");
            }
            System.out.println("Всего задач: " + periodicTasks.size());
            System.out.println("==============================================");
        } else {
            System.out.println("ВНИМАНИЕ: Нет зарегистрированных периодических задач.");
            System.out.println("Проверьте:");
            System.out.println("  1. Класс имеет аннотацию @Component");
            System.out.println("  2. Класс реализует интерфейс PeriodicTask");
            System.out.println("  3. Класс находится в пакете com.example.StandHealthMonitor или его подпакетах");
            System.out.println("  4. Приложение перезапущено после добавления класса");
        }
        
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "periodic-task-thread");
            thread.setDaemon(true);
            return thread;
        });
        
        // Запускаем задачу сразу и затем каждые 60 минут
        scheduler.scheduleAtFixedRate(
            this::executePeriodicTasks,
            0,
            60,
            TimeUnit.MINUTES
        );
    }

    @PreDestroy
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Выполняет все зарегистрированные периодические задачи.
     * 
     * КАК ЭТО РАБОТАЕТ:
     * ==================
     * Spring автоматически находит ВСЕ классы с аннотацией @Component,
     * которые реализуют интерфейс PeriodicTask, и автоматически добавляет
     * их в список через механизм Dependency Injection.
     * 
     * ВАМ НЕ НУЖНО ВРУЧНУЮ ДОБАВЛЯТЬ ЗАДАЧИ В СПИСОК!
     * 
     * Чтобы добавить свою задачу:
     * 1. Создайте класс, реализующий интерфейс PeriodicTask
     * 2. Добавьте аннотацию @Component
     * 3. Реализуйте метод execute()
     * 4. Перезапустите приложение
     * 
     * Пример:
     * @Component
     * public class MyTask implements PeriodicTask {
     *     @Override
     *     public RsStatObj execute() {
     *         // ваша логика
     *         return new RsStatObj(200, "SystemName");
     *     }
     * }
     * 
     * Spring автоматически найдет этот класс и добавит его в список periodicTasks!
     */
    private void executePeriodicTasks() {
        if (periodicTasks == null || periodicTasks.isEmpty()) {
            System.out.println("Нет зарегистрированных периодических задач");
            return;
        }
        
        System.out.println("Выполнение периодических задач. Количество: " + periodicTasks.size());
        
        for (PeriodicTask task : periodicTasks) {
            try {
                String taskName = task.getClass().getSimpleName();
                System.out.println("Выполнение задачи: " + taskName);
                PingResponse rs = task.execute();

                if (rs != null) {
                    // Сохраняем или обновляем статус в БД
                    updateOrCreateSystemStatus(rs);
                    System.out.println("Статус сохранен: система=" + rs.getSystemName() + ", статус=" + rs.getStatusCode());
                } else {
                    System.out.println("Задача " + taskName + " вернула null, статус не сохранен");
                }

                System.out.println("Задача " + taskName + " выполнена успешно");
            } catch (Exception e) {
                String taskName = task.getClass().getSimpleName();
                System.err.println("Ошибка при выполнении задачи " + taskName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Обновляет или создает запись о статусе системы в БД.
     * 
     * Логика:
     * 1. Извлекает данные из RsStatObj (система и статус)
     * 2. Проверяет наличие записи за сегодня по данной системе и статусу
     * 3. Если запись существует - увеличивает счетчик на 1
     * 4. Если записи нет - создает новую со значением count=1
     * 
     * @param rs объект с данными о статусе системы
     */
    @Transactional
    public void updateOrCreateSystemStatus(PingResponse rs) {
        if (rs == null || rs.getSystemName() == null || rs.getSystemName().trim().isEmpty()) {
            System.err.println("RsStatObj содержит некорректные данные, пропускаем сохранение");
            return;
        }

        LocalDate today = LocalDate.now();
        String systemName = rs.getSystemName().trim();
        String status = String.valueOf(rs.getStatusCode()); // Преобразуем int в String

        // Ищем существующую запись за сегодня
        SystemStatus existingStatus = systemStatusRepository
                .findByDateAndSystemNameAndStatus(today, systemName, status)
                .orElse(null);

        if (existingStatus != null) {
            // Запись существует - увеличиваем счетчик и обновляем httpCode/success
            existingStatus.setCount(existingStatus.getCount() + 1);
            existingStatus.setHttpCode(rs.getHttpCode());
            existingStatus.setSuccess(rs.isSuccess());
            systemStatusRepository.save(existingStatus);
            System.out.println("Обновлена запись: система=" + systemName + ", статус=" + status + 
                             ", новый count=" + existingStatus.getCount());
        } else {
            // Записи нет - создаем новую со значением count=1 и полями из PingResponse
            SystemStatus newStatus = new SystemStatus(status, systemName, today, 1,
                    rs.getHttpCode(), rs.isSuccess());
            systemStatusRepository.save(newStatus);
            System.out.println("Создана новая запись: система=" + systemName + ", статус=" + status + 
                             ", count=1");
        }
    }
}
