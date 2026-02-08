package com.example.StandHealthMonitor.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Универсальный Kafka-продюсер с эфемерным подключением.
 * <p>
 * Принцип работы: создаётся при вызове send(), отправляет сообщение и закрывает соединение.
 * Постоянное подключение к брокеру не поддерживается.
 * <p>
 * Пример использования в System-классе:
 * <pre>
 * KafkaProducerConfig config = KafkaProducerConfig.builder()
 *     .bootstrapServers("broker1:9093,broker2:9093")
 *     .topic("my-topic")
 *     .truststorePath("./certs/system-a-truststore.jks")
 *     .truststorePassword("password")
 *     .build();
 *
 * KafkaMessageSender sender = new KafkaMessageSender(config);
 * sender.send("{\"key\":\"value\"}", Map.of("X-Correlation-Id", "123"));
 * </pre>
 */
public class KafkaMessageSender {

    private final KafkaProducerConfig config;

    public KafkaMessageSender(KafkaProducerConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("config не может быть null");
        }
        this.config = config;
    }

    /**
     * Отправляет сообщение в Kafka и закрывает соединение.
     *
     * @param body    тело сообщения (JSON, текст и т.д.)
     * @param headers заголовки (ключ → значение), может быть null или пустой
     * @return Future с метаданными записи (для опциональной проверки результата)
     */
    public Future<RecordMetadata> send(String body, Map<String, String> headers) {
        return send(config.getTopic(), body, headers);
    }

    /**
     * Отправляет сообщение в указанный топик и закрывает соединение.
     *
     * @param topic   топик (переопределяет топик из конфига)
     * @param body    тело сообщения
     * @param headers заголовки, может быть null или пустой
     * @return Future с метаданными записи
     */
    public Future<RecordMetadata> send(String topic, String body, Map<String, String> headers) {
        KafkaProducer<String, String> producer = createProducer();
        try {
            ProducerRecord<String, String> record = buildRecord(topic, body, headers);
            Future<RecordMetadata> future = producer.send(record);
            producer.flush();
            return future;
        } finally {
            producer.close();
        }
    }

    /**
     * Синхронная отправка: ждёт подтверждения от брокера.
     */
    public RecordMetadata sendSync(String body, Map<String, String> headers) throws Exception {
        return send(config.getTopic(), body, headers).get();
    }

    /**
     * Синхронная отправка в указанный топик.
     */
    public RecordMetadata sendSync(String topic, String body, Map<String, String> headers) throws Exception {
        return send(topic, body, headers).get();
    }

    private KafkaProducer<String, String> createProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", config.getBootstrapServers());
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        if (config.isSslEnabled()) {
            props.put("security.protocol", "SSL");
            props.put("ssl.truststore.location", config.getTruststorePath());
            props.put("ssl.truststore.password", config.getTruststorePassword());
            props.put("ssl.truststore.type", "JKS");

            if (config.hasClientAuth()) {
                props.put("ssl.keystore.location", config.getKeystorePath());
                props.put("ssl.keystore.password", config.getKeystorePassword());
                props.put("ssl.keystore.type", "JKS");
                String keyPassword = config.getKeyPassword() != null ? config.getKeyPassword() : config.getKeystorePassword();
                props.put("ssl.key.password", keyPassword);
            }
        } else {
            props.put("security.protocol", "PLAINTEXT");
        }

        return new KafkaProducer<>(props);
    }

    private ProducerRecord<String, String> buildRecord(String topic, String body, Map<String, String> headers) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, body);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                if (e.getKey() != null && e.getValue() != null) {
                    record.headers().add(new RecordHeader(e.getKey(), e.getValue().getBytes(StandardCharsets.UTF_8)));
                }
            }
        }

        return record;
    }
}
