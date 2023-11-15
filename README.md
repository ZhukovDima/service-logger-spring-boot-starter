[![Run all tests](https://github.com/ZhukovDima/multimodule-demo-project/actions/workflows/project-run-tests.yml/badge.svg)](https://github.com/ZhukovDima/multimodule-demo-project/actions/workflows/project-run-tests.yml)

Maven Multi-module Spring Boot Starter
===============================
Проект с двумя модулями (maven) и общим родителем.

Модуль 1:
Библиотека-стартер на основе Spring Boot автоконфигураций, делающая так, что все вызовы публичных методов компонентов, отмеченных Spring-аннотацией @Service, будут логироваться сообщением "{methodName} args: [{args}]" (например: "saveFile args: [ "order.pdf", 123, "application/pdf"]").
Отключение автоконфигурации библиотеки  через spring-property "libname.enabled". Возможность не логировать определённые аргументы. Имя логгера соответствует имени класса сервиса.
Тесты на автоконфигурацию.

Модуль 2:
Web приложение при помощи Spring Boot, с двумя REST-методами - сохранить файл, получить файл. Хранение файла в БД (Postgres). Размер файла - не более 1 мб. 
Интеграционные @SpringBootTest тесты на REST-методы с использованием mockMvc и тестовой БД через testcontainers.

