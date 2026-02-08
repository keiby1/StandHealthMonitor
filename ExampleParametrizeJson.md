# Примеры вызова 
```
// Без параметров
json = TemplatesHolder.getTemplate("A1", "prep");
// → {"system": "A1", "step": "prep", "requestId": "${requestId}", "timestamp": "${timestamp}"} 

// С параметрамиString json = 
TemplatesHolder.getTemplate("A1", "prep", Map.of(    
    "requestId", "12345",    
    "timestamp", "2026-02-08T16:00:00"));
    
// → {"system": "A1", "step": "prep", "requestId": "12345", "timestamp": "2026-02-08T16:00:00"}
```