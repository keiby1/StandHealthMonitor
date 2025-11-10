-- SQL скрипт для удаления последовательности system_statuses_id_seq
-- Выполните этот скрипт в вашем клиенте PostgreSQL (pgAdmin, DBeaver, psql и т.д.)

-- Удаляем последовательность, которая вызывает конфликт
DROP SEQUENCE IF EXISTS system_statuses_id_seq CASCADE;

-- После выполнения этого скрипта перезапустите приложение
-- Hibernate создаст таблицу с правильным типом BIGSERIAL для IDENTITY стратегии

