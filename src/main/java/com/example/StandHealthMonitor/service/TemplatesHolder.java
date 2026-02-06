package com.example.StandHealthMonitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Загружает при старте приложения все JSON-файлы из classpath:templates/{названиеСистемы}/{названиеШага}.json,
 * сохраняет их в памяти и отдаёт по запросу без повторного чтения с диска.
 * <p>
 * Инициализация выполняется до {@link PeriodicTaskService} за счёт {@code @DependsOn("templatesHolder")}.
 * <p>
 * Пример использования в классе системы:
 * <pre>
 * &#64;Autowired
 * private TemplatesHolder templatesHolder;
 *
 * String json = templatesHolder.getTemplate("A1", "prep");  // содержимое templates/A1/prep.json
 * </pre>
 */
@Component("templatesHolder")
@Order(Integer.MIN_VALUE)
public class TemplatesHolder {

    private static final String TEMPLATES_PATTERN = "classpath*:templates/*/*.json";
    private static final Logger log = LoggerFactory.getLogger(TemplatesHolder.class);

    /**
     * systemName -> (stepName -> содержимое JSON как строка).
     * stepName — имя файла без расширения .json.
     */
    private final Map<String, Map<String, String>> templatesBySystem = new HashMap<>();

    @PostConstruct
    public void loadTemplates() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(TEMPLATES_PATTERN);
            for (Resource resource : resources) {
                if (!resource.isReadable()) continue;
                String systemName = null;
                String stepName = null;
                try {
                    String path = resource.getURI().toString();
                    int idx = path.indexOf("templates/");
                    if (idx < 0) continue;
                    String relative = path.substring(idx + "templates/".length());
                    int slash = relative.indexOf('/');
                    if (slash < 0) continue;
                    systemName = relative.substring(0, slash);
                    String fileName = relative.substring(slash + 1);
                    if (fileName.endsWith(".json")) {
                        stepName = fileName.substring(0, fileName.length() - 5);
                    } else {
                        continue;
                    }
                } catch (Exception e) {
                    log.warn("Не удалось разобрать путь ресурса: {}", resource.getDescription(), e);
                    continue;
                }
                String content = readResource(resource);
                if (content != null) {
                    templatesBySystem
                            .computeIfAbsent(systemName, k -> new HashMap<>())
                            .put(stepName, content);
                }
            }
            log.info("Загружено шаблонов: {} систем, всего файлов: {}",
                    templatesBySystem.size(),
                    templatesBySystem.values().stream().mapToInt(Map::size).sum());
        } catch (IOException e) {
            log.error("Ошибка сканирования шаблонов по шаблону {}", TEMPLATES_PATTERN, e);
        }
    }

    /**
     * Возвращает содержимое JSON-шаблона для системы и шага, или null, если не найден.
     *
     * @param systemName название системы (папка в templates/)
     * @param stepName   название шага (имя файла без .json)
     */
    public String getTemplate(String systemName, String stepName) {
        if (systemName == null || stepName == null) return null;
        Map<String, String> steps = templatesBySystem.get(systemName);
        return steps != null ? steps.get(stepName) : null;
    }

    /**
     * Все загруженные шаблоны: systemName -> (stepName -> json string).
     */
    public Map<String, Map<String, String>> getAllTemplates() {
        Map<String, Map<String, String>> copy = new HashMap<>();
        templatesBySystem.forEach((sys, steps) -> copy.put(sys, new HashMap<>(steps)));
        return copy;
    }

    private String readResource(Resource resource) {
        try (Reader r = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[2048];
            int n;
            while ((n = r.read(buf)) >= 0) {
                sb.append(buf, 0, n);
            }
            return sb.toString();
        } catch (IOException e) {
            log.warn("Ошибка чтения шаблона {}: {}", resource.getDescription(), e.getMessage());
            return null;
        }
    }
}
