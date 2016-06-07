#!/bin/sh
sudo docker run -d -e RABBITMQ='145.24.222.140' -e RABBITMQ_USER="0885083" -e RABBITMQ_PASS="awesomePassword23" -e RABBITMQ_VIRTUAL='NodeManager' -e RABBITMQ_PORT='5672' -e RABBITMQ_MANAGEMENT_PORT=':8080' -e MYSQL='145.24.222.223:3306' -e MYSQL_USER='0882805' -e MYSQL_PASS='100ddJ' -p 8080:8080 --name nodemanager -t nodemanager
