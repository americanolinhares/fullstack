docker-compose down

docker build -t backend-acme:latest ./backend

docker build -t frontend-acme:latest ./frontend

docker-compose up --build --force-recreate --remove-orphans