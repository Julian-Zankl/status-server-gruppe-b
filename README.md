# Status Server Gruppe B

## Aufbau

Für das Frontend und die Nodes wurden jeweils ein eigenes `Dockerfile` kreiert, welches zum Bauen der Container genutzt wird.
Zudem wurde ein `docker-compose.yml` angelegt, damit sämtliche Applikationen mittels eines Befehls gebaut und ausgeführt werden können.

Ebenso wichtig ist die `nginx.conf`, welche die Proxy-Einstellungen für das Frontend beinhaltet.
Sie legt auch fest, dass alle Requests mit dem Präfix `/api` an das API-Gateway weitergeleitet werden.

## Frontend

Wurde mithilfe von Angular auf Basis von Angular Material umgesetzt.
Bietet eine simple Navigation via einer Sidenav, welche sämtliche CRUD-Befehle ermöglicht.

Port: `4200`

## Node

Wurde auf Basis von Spring Boot (Java) implementiert.
Bietet einfache CRUD-Befehle für den Umgang von Benutzerstatus.

Port: `8089`

## API Gateway

Port: `8080`

## Service Registry

Port: `8761`

## RabbitMQ

Port: `15672`

## Start der Applikation

Mittels `docker-compose up` können sämtliche Applikationen als Docker Container gestartet werden.

## Skalierung mehrerer Instanzen (Nodes)

Mithilfe des Batch-Skripts `start_cluster.bat` können mehrere Nodes erzeugt werden.
Hierbei kann innerhalb des Skripts die Anzahl der Nodes definiert werden → z.B. `--scale node=2`

## Stopp der Applikation

Mithilfe des Batch-Skripts `stop_cluster.bat` werden alle Docker Container beendet.