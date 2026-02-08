package com.example.StandHealthMonitor.kafka;

/**
 * Конфигурация для подключения к Kafka.
 * Параметры различаются для каждой системы (брокеры, топики, сертификаты JKS).
 */
public class KafkaProducerConfig {

    private final String bootstrapServers;
    private final String topic;
    private final String truststorePath;
    private final String truststorePassword;
    private final String keystorePath;
    private final String keystorePassword;
    private final String keyPassword;
    private final String keystoreAlias;

    private KafkaProducerConfig(Builder builder) {
        this.bootstrapServers = builder.bootstrapServers;
        this.topic = builder.topic;
        this.truststorePath = builder.truststorePath;
        this.truststorePassword = builder.truststorePassword;
        this.keystorePath = builder.keystorePath;
        this.keystorePassword = builder.keystorePassword;
        this.keyPassword = builder.keyPassword;
        this.keystoreAlias = builder.keystoreAlias;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getTopic() {
        return topic;
    }

    public String getTruststorePath() {
        return truststorePath;
    }

    public String getTruststorePassword() {
        return truststorePassword;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public String getKeystoreAlias() {
        return keystoreAlias;
    }

    /** Проверка, требуется ли SSL (есть truststore) */
    public boolean isSslEnabled() {
        return truststorePath != null && !truststorePath.isBlank();
    }

    /** Проверка, требуется ли клиентская аутентификация (keystore) */
    public boolean hasClientAuth() {
        return keystorePath != null && !keystorePath.isBlank();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String bootstrapServers;
        private String topic;
        private String truststorePath;
        private String truststorePassword;
        private String keystorePath;
        private String keystorePassword;
        private String keyPassword;
        private String keystoreAlias;

        public Builder bootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
            return this;
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        /** Путь к JKS хранилищу доверенных сертификатов (для проверки брокера) */
        public Builder truststorePath(String truststorePath) {
            this.truststorePath = truststorePath;
            return this;
        }

        public Builder truststorePassword(String truststorePassword) {
            this.truststorePassword = truststorePassword;
            return this;
        }

        /** Путь к JKS хранилищу клиента (для взаимной аутентификации, опционально) */
        public Builder keystorePath(String keystorePath) {
            this.keystorePath = keystorePath;
            return this;
        }

        public Builder keystorePassword(String keystorePassword) {
            this.keystorePassword = keystorePassword;
            return this;
        }

        /** Пароль к приватному ключу (если отличается от keystore) */
        public Builder keyPassword(String keyPassword) {
            this.keyPassword = keyPassword;
            return this;
        }

        /** Alias сертификата в keystore (опционально) */
        public Builder keystoreAlias(String keystoreAlias) {
            this.keystoreAlias = keystoreAlias;
            return this;
        }

        public KafkaProducerConfig build() {
            if (bootstrapServers == null || bootstrapServers.isBlank()) {
                throw new IllegalArgumentException("bootstrapServers обязателен");
            }
            if (topic == null || topic.isBlank()) {
                throw new IllegalArgumentException("topic обязателен");
            }
            if (truststorePath != null && !truststorePath.isBlank()) {
                if (truststorePassword == null || truststorePassword.isBlank()) {
                    throw new IllegalArgumentException("truststorePassword обязателен при использовании truststore");
                }
            }
            return new KafkaProducerConfig(this);
        }
    }
}
