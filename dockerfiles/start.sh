#!/bin/sh
sudo docker run -d -e RABBITMQ='145.24.222.140' -e RABBITMQ_USER="0885083" -e RABBITMQ_PASS="awesomePassword23" -p 8080:8080 --name nodemanager -t nodemanager