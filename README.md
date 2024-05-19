# Status Server Gruppe B

## Frontend

Wurde mithilfe von Angular auf Basis von Angular Material umgesetzt.
Bietet eine simple Navigation via einer Sidenav, welche sämtliche CRUD-Befehle ermöglicht.

Port: `4200`

## Backend

Wurde auf Basis von Spring Boot (Java) implementiert.
Bietet einfache CRUD-Befehle für den Umgang von Benutzerstatus.

Port: `8080`

## Start der Applikation

Mittels `docker-compose up` können beide Projekte als Docker Container gebaut und ausgeführt werden.
So wird für beide Applikationen jeweils ein Docker Container erstellt, die miteinander kommunizieren können.

