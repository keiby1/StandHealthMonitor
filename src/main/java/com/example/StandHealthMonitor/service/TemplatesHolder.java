package com.example.StandHealthMonitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Загружает при загрузке класса все JSON-файлы из classpath:templates/{названиеСистемы}/{названиеШага}.json,
 * сохраняет их в памяти и отдаёт по запросу без повторного чтения с диска.
 * <p>
 * Поддерживает параметризацию: в JSON используйте {@code ${var}} для подстановки значений.
 * Ключи {@code system} и {@code step} подставляются автоматически.
 * <p>
 * Пример JSON (templates/A1/prep.json):
 * <pre>
 * {"system": "${system}", "requestId": "${requestId}"}
 * </pre>
 * <p>
 * Пример вызова:
 * <pre>
 * String json = TemplatesHolder.getTemplate("A1", "prep");  // без параметров
 * String json = TemplatesHolder.getTemplate("A1", "prep", Map.of("requestId", "12345"));  // с параметрами
 * </pre>
 */
public final class TemplatesHolder {

    private static final String TEMPLATES_PATTERN = "classpath*:templates/*/*.json";
    private static final Logger log = LoggerFactory.getLogger(TemplatesHolder.class);

    /**
     * systemName -> (stepName -> содержимое JSON как строка).
     * stepName — имя файла без расширения .json.
     */
    private static final Map<String, Map<String, String>> templatesBySystem = new HashMap<>();

    static {
        loadTemplates();
    }

    private TemplatesHolder() {
        // запрет создания экземпляров
    }

    private static void loadTemplates() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(TEMPLATES_PATTERN);
            for (Resource resource : resources) {
                if (!resource.isReadable()) continue;
                String systemName = null;
                String stepName = null;
                try {
                    String path = resource.getURI().toString();
                    // Support both forward and backslash (Windows)
                    int idx = path.indexOf("templates/");
                    if (idx < 0) idx = path.indexOf("templates\\");
                    if (idx < 0) continue;
                    String relative = path.substring(idx + "templates/".length()).replace('\\', '/');
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
                    log.warn("Failed to parse resource path: {}", resource.getDescription(), e);
                    continue;
                }
                String content = readResource(resource);
                if (content != null) {
                    templatesBySystem
                            .computeIfAbsent(systemName, k -> new HashMap<>())
                            .put(stepName, content);
                }
            }
            int totalFiles = templatesBySystem.values().stream().mapToInt(Map::size).sum();
            log.info("Loaded templates: {} systems, {} files total",
                    templatesBySystem.size(), totalFiles);
        } catch (IOException e) {
            log.error("Failed to scan templates with pattern {}", TEMPLATES_PATTERN, e);
        }
    }

    private static String getRawTemplate(String systemName, String stepName) {
        if (systemName == null || stepName == null) return null;
        Map<String, String> steps = templatesBySystem.get(systemName);
        return steps != null ? steps.get(stepName) : null;
    }

    /**
     * Возвращает содержимое JSON-шаблона для системы и шага, или null, если не найден.
     *
     * @param systemName название системы (папка в templates/)
     * @param stepName   название шага (имя файла без .json)
     */
    public static String getTemplate(String systemName, String stepName) {
        return getTemplate(systemName, stepName, Collections.emptyMap());
    }

    /**
     * Возвращает JSON-шаблон с подстановкой параметров.
     * Плейсхолдеры в формате {@code ${var}} заменяются на значения из params.
     * Ключи {@code system} и {@code step} подставляются автоматически (из systemName и stepName).
     *
     * @param systemName название системы (папка в templates/)
     * @param stepName   название шага (имя файла без .json)
     * @param params     дополнительные параметры для подстановки (ключ — имя переменной без ${})
     * @return заполненный JSON или null, если шаблон не найден
     */
    public static String getTemplate(String systemName, String stepName, Map<String, String> params) {
        String raw = getRawTemplate(systemName, stepName);
        if (raw == null) return null;

        Map<String, String> allParams = new HashMap<>();
        allParams.put("system", systemName);
        allParams.put("step", stepName);
        if (params != null) allParams.putAll(params);

        return replacePlaceholders(raw, allParams);
    }

    /**
     * Заменяет все вхождения ${key} на соответствующее значение из params.
     * Использует String.replace для надёжности (без regex).
     */
    private static String replacePlaceholders(String template, Map<String, String> params) {
        String result = template;
        for (Map.Entry<String, String> e : params.entrySet()) {
            String placeholder = "${" + e.getKey() + "}";
            result = result.replace(placeholder, e.getValue());
        }
        return result;
    }

    /**
     * Все загруженные шаблоны: systemName -> (stepName -> json string).
     */
    public static Map<String, Map<String, String>> getAllTemplates() {
        Map<String, Map<String, String>> copy = new HashMap<>();
        templatesBySystem.forEach((sys, steps) -> copy.put(sys, new HashMap<>(steps)));
        return Collections.unmodifiableMap(copy);
    }

    private static String readResource(Resource resource) {
        try (Reader r = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[2048];
            int n;
            while ((n = r.read(buf)) >= 0) {
                sb.append(buf, 0, n);
            }
            return sb.toString();
        } catch (IOException e) {
            log.warn("Failed to read template {}: {}", resource.getDescription(), e.getMessage());
            return null;
        }
    }
}
