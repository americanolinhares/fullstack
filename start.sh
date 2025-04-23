docker-compose down

docker build -t backend-natixis:latest ./backend

docker build -t frontend-natixis:latest ./frontend

docker-compose up --build --force-recreate --remove-orphans