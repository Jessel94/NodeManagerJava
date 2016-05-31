#!/bin/sh
sudo docker run -d -e RABBITMQ="http://145.24.222.140/" -p 8080:8080 --name nodemanager -t nodemanager