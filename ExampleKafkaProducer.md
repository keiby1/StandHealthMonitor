# Пример использования в execute() класса системы
```@Overridepublic PingResponse execute() {    
String rqJson = TemplatesHolder.getTemplate("A1", "prep", Map.of("test", "123"));        
KafkaProducerConfig config = KafkaProducerConfig.builder()        
.bootstrapServers("kafka-broker:9093")        
.topic("system-a1-events")        
.truststorePath("./certs/a1-truststore.jks")        
.truststorePassword("secret")        
.build();        

KafkaMessageSender sender = new KafkaMessageSender(config);    
sender.send(rqJson, Map.of(        
"X-System-Id", "A1",        
"X-Correlation-Id", UUID.randomUUID().toString()    
));        

return new PingResponse("A1", "System A", 200, "200", "Все ок", true);}
```

Для взаимной аутентификации добавьте .keystorePath(...), .keystorePassword(...) и при необходимости .keystoreAlias(...) в цепочку конфигурации.