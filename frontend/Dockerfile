FROM node:18

WORKDIR /app

COPY package*.json  ./
COPY public ./public
COPY src ./src

RUN npm install

COPY . .

RUN npm run build

CMD ["npm", "run", "--", "--port", "3000", "--host", "0.0.0.0"]