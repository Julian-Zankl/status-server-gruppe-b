@echo off
docker compose -f docker-compose.yml up --scale node=3
