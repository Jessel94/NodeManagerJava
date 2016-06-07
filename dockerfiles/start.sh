#!/bin/sh
sudo docker run -d -e RABBITMQ='145.24.222.140' -e RABBITMQ_USER="0885083" -e RABBITMQ_PASS="awesomePassword23" -e RABBITMQ_VIRTUAL='NodeManager' -e RABBITMQ_PORT='5672' -p 8080:8080 --name nodemanager -t nodemanager
