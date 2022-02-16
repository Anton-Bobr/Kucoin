Описание репозитория:

11-hello - java обучение запуску в консоли.
12-hello - python обучение запуску в консоли.

13-Kucoin - знакомство с парадигмами ООП. Работа с API сайтов, знакомство с потоками, форматом json

14-Whale-Web - простой проект.

ТЗ:
- парсинг телеграм канала и запись в БД
- веб сервер, отдающий данные из БД на веб страницу

Знакомство с технологиями:
- Docker
- jdbc
- SQL
- JS
- gradle многомодульный проект

Для запуска Whale-serer достаточно build.gradle и docker-compose

Для запуска Whale-telegram-pars нужно создать файл:

resources/META-INF/microprofile-config.properties

и заполнить следующие переменные своими данными:

telegram.my.api.id=
telegram.my.api.hash =
telegram.my.api.phone=
telegram.id.Whale.Alert=-1001309043988