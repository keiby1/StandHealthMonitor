-- SQL скрипт для исправления проблемы с последовательностью system_statuses_id_seq
-- Выполните этот скрипт перед перезапуском приложения

-- 1. Удаляем последовательность, если она была создана ранее
DROP SEQUENCE IF EXISTS system_statuses_id_seq CASCADE;

-- 2. Проверяем текущий тип колонки id
SELECT 
    column_name, 
    data_type, 
    column_default,
    is_nullable
FROM information_schema.columns 
WHERE table_name = 'system_statuses' AND column_name = 'id';

-- 3. Если колонка не BIGSERIAL, пересоздаём её как BIGSERIAL
-- ВНИМАНИЕ: Это удалит все данные! Выполняйте только если таблица пустая или данные не важны!
-- Если в таблице есть данные, используйте вариант ниже (шаг 4)

-- Вариант для пустой таблицы:
-- DROP TABLE IF EXISTS system_statuses CASCADE;
-- Hibernate создаст таблицу заново при следующем запуске

-- 4. Вариант для таблицы с данными (сохраняет данные):
-- Сначала создаем временную таблицу с данными
-- CREATE TABLE system_statuses_temp AS SELECT * FROM system_statuses;
-- 
-- -- Удаляем старую таблицу
-- DROP TABLE system_statuses CASCADE;
-- 
-- -- Hibernate создаст таблицу заново при следующем запуске
-- -- Затем вручную восстановите данные из system_statuses_temp

-- 5. После перезапуска приложения Hibernate создаст таблицу с правильным типом BIGSERIAL
-- Проверьте, что колонка создана правильно:
-- SELECT column_name, data_type, column_default 
-- FROM information_schema.columns 
-- WHERE table_name = 'system_statuses' AND column_name = 'id';
-- 
-- Должно быть: data_type = 'bigint', column_default должно содержать 'nextval'
