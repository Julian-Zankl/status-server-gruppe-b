@echo off
docker compose -f docker-compose.yml up --build --scale node=3
