
# CRUDApplication

A simple Spring Boot REST API for managing Books with full CRUD operations, logging, and Swagger API documentation.

---

## Features

- Create, Read, Update, Delete books  
- Auto-generate UUID for missing/invalid ISBN  
- Layered architecture: Controller → Service → Repository  
- Global exception handling with meaningful responses  
- SLF4J logging at key steps  
- Interactive Swagger UI docs  
- In-memory H2 database for easy testing  

---

## Tech Stack

- Java 17, Spring Boot 3.2.2  
- Spring Data JPA, H2 Database  
- Lombok, SLF4J / Logback  
- springdoc-openapi (Swagger)  
- JUnit 5, Mockito  
- Maven  

---

## Getting Started

### Clone the repository

```bash
git clone https://github.com/yourusername/CRUDApplication.git
cd CRUDApplication
```

### Build & Run Locally

```bash
mvn clean install
mvn spring-boot:run
```

**Swagger API docs:**  
[http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

---

## API Endpoints

| Method | Endpoint                 | Description          |
| ------ | ------------------------ | ------------------ |
| GET    | `/book/getallbooks`      | Get all books        |
| GET    | `/book/getbookbyid/{id}` | Get book by ID       |
| POST   | `/book/addbook`          | Add a new book       |
| PUT    | `/book/updatebook/{id}`  | Update existing book |
| DELETE | `/book/deletebook/{id}`  | Delete book by ID    |

**Notes:**

- Book with ID 2 is restricted (cannot access).  
- ISBN is auto-set to UUID if missing or `"null"`.  
- Data is stored in H2 in-memory DB; resets on restart.  

---

## Testing

Run unit tests:

```bash
mvn test
```

---

## Running with Docker

### 1️⃣ Build the Spring Boot JAR

```bash
mvn clean package -DskipTests
```

### 2️⃣ Build Docker image

```bash
docker build -t crudapp:latest .
```

### 3️⃣ Run container for the first time

```bash
docker run -p 9090:9090 --name crudapp crudapp:latest
```

- App: `http://localhost:9090`  
- H2 console: `http://localhost:9090/h2`  

---

### 4️⃣ Reusing an existing container

If a container already exists (stopped), you can restart it:

```bash
docker ps -a       # Check container exists
docker start -a crudapp   # Start and attach logs
```

---

### 5️⃣ Removing a container

If you need to remove an existing container (e.g., to rerun with different options):

```bash
docker stop crudapp       # Stop if running
docker rm crudapp         # Remove the container
```

Then run again:

```bash
docker run -p 9090:9090 --name crudapp crudapp:latest
```

---

### 6️⃣ Handling container name conflicts

If you see an error like:

```
docker: Error response from daemon: Conflict. The container name "/crudapp" is already in use
```

- **Option A:** Remove the old container (see step 5)  
- **Option B:** Use a different name for the new container:

```bash
docker run -p 9090:9090 --name crudapp2 crudapp:latest
```

---

### 7️⃣ Using Docker Compose (Optional)

If you have `docker-compose.yml`:

```bash
docker compose up --build
```

Stop containers:

```bash
docker compose down
```

List running containers:

```bash
docker ps
```

List all containers (including stopped):

```bash
docker ps -a
```
