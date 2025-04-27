# Backend and Frontend - E-commerce Product Catalog

## Technologies Used

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.4.4
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Documentation**: Swagger UI
- **Authentication**: JWT (JSON Web Token)
- **Testing**: JUnit, Mockito, Spring Security Test

### Frontend
- **Language**: TypeScript
- **Framework**: React
- **Package Manager**: Yarn, npm
- **Styling**: Bootstrap

---

## Features

### Backend
- CRUD for Product and Login and Registration for User.
- User authentication and authorization with JWT.
- API documentation with Swagger UI.
- SQL scripts to populate the database with test data.

### Frontend
- User interface to manage products.
- Forms for user registration and login.
- Display of error and success messages.
- Automatic redirection after actions like login and registration.

---

## Running with Docker

You can run the application using Docker and `docker-compose`. A `start.sh` script is also provided to simplify the process.

### Steps to Run with Docker
1. Ensure Docker and Docker Compose are installed on your system.

2. Clone the repository:
   ```bash
   git clone https://github.com/americanolinhares/fullstack.git
   cd fullstack
   ```

3. Make the `start.sh` script executable:
   ```bash
   chmod +x start.sh
   ```

4. Run the application:
   ```bash
   ./start.sh
   ```

5. The backend will be available at: [http://localhost:8080](http://localhost:8080)  
   The frontend will be available at: [http://localhost:3000](http://localhost:3000)

If you prefer to run manually with `docker-compose`, use the following command:
```bash
docker-compose up --build
```

## Steps to Run without Docker

### Prerequisites
- **Backend**:
    - Java Development Kit (JDK) 17 or higher
    - Maven 3.1.1+
    - PostgreSQL
    - Git
    - IDE (optional): IntelliJ IDEA

- **Frontend**:
    - Node.js 16+
    - Yarn or npm
    - Git
    - IDE (optional): IntelliJ IDEA or similar

---

### Steps to Run

#### Backend
1. Clone the repository:
   ```bash
   git clone https://github.com/americanolinhares/fullstack.git
   cd backend
   ```

2. Configure the database in the `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/backenddb
   spring.datasource.username=backend
   spring.datasource.password=backend
   ```

3. Build and run the project:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. The API will be available at: [http://localhost:8080](http://localhost:8080)

#### Frontend
1. Clone the repository:
   ```bash

   git clone https://github.com/americanolinhares/fullstack.git
   cd frontend
   ```

2. Install dependencies:
   ```bash
   yarn install
   # or
   npm install
   ```

3. Start the development server:
   ```bash
   yarn start
   # or
   npm start
   ```

4. The application will be available at: [http://localhost:3000](http://localhost:3000)

---

## API Documentation

The API documentation is available via Swagger UI.

- **URL**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Testing

### Backend
- Run tests with:
  ```bash
  mvn test
  ```

---

## Authentication

The API uses JWT to protect routes. To access protected routes, obtain a JWT token from the `/login` endpoint and include it in the `Authorization` header as a Bearer token.

### Request Example (this user was added to the database for testing)
**Endpoint**: `POST /login`

**Body**:
```json
{
  "username": "fred",
  "password": "12345678"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

Include the token in the header to access protected routes:
```
Authorization: Bearer <your_token>
```

## Testing with Postman

Postman files are included in the repository to help you test the API endpoints.

### Importing the Collection and Environment
1. Open Postman.
2. Click on **Import** in the top-left corner.
3. Import the following files from the repository:
    - `Natixis API Collection.postman_collection.json`: Contains pre-configured requests for all API endpoints.
    - `NatixisEnviroment.postman_environment.json`: Contains environment variables like `accessToken` to simplify testing.

### Using the Collection
1. Select the imported environment (`NatixisEnviroment`) in Postman.
2. Use the pre-configured requests in the collection to test the API endpoints. The `Authorization` header will automatically include the token.
3. Run the collection to test all endpoints in sequence.

### Example Workflow
1. **Login**: Use the `POST /login` request to authenticate and retrieve a JWT token.
2. **Test Endpoints**: Use requests like `GET /product`, `POST /product`, etc., to interact with the API.

Ensure the backend is running at `http://localhost:8080` before testing.

---

## Contact

For questions or clarifications, contact: **americanolinhares@gmail.com**