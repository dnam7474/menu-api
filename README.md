# Menu API - Restaurant Ordering System

A RESTful web service built with Spring Boot that facilitates ordering dinner at a restaurant. This service serves as the backend for a React front-end, enabling users to browse the menu, place orders, and manage their dining experience.

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Database Setup](#database-setup)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Configuration](#configuration)
- [Extra Features](#extra-features)
- [Frontend Integration](#frontend-integration)

## Overview

This application provides a complete restaurant ordering system with the following features:
- User management with role-based access (ADMIN, USER, SERVER)
- Menu item management
- Order processing and tracking
- Film catalog for "Dinner & a Movie" experience
- Authentication and authorization
- Static image serving

## Prerequisites

Before running this application, ensure you have:

- **Java 21** or higher
- **Maven 3.9+** (or use the included Maven wrapper)
- **MySQL/MariaDB** database server
- **Git** (for version control)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Optional Tools
- **DBeaver** or **MySQL Workbench** (for database management)
- **Postman** (for API testing)
- **Docker** (if you prefer containerized deployment)

## Project Structure

```
menu-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/simple/menu_api/
│   │   │       ├── MenuApiApplication.java
│   │   │       ├── controllers/        # REST controllers
│   │   │       ├── entities/           # JPA entities
│   │   │       ├── repositories/       # Data repositories
│   │   │       └── services/           # Business logic
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql               # Sample data
│   │       └── static/images/         # Static images
│   └── test/                          # Unit tests
├── database_scripts/                  # Database setup scripts
├── Dockerfile                        # Docker configuration
├── pom.xml                           # Maven dependencies
└── README.md                         # This file
```

## Database Setup

### 1. Create Database

**Option A: Using MySQL Command Line**
```sql
mysql -u root -p
CREATE DATABASE daamdb;
EXIT;
```

**Option B: Using Database Management Tool**
- Open DBeaver or MySQL Workbench
- Connect to your MySQL server
- Create a new database named `daamdb`

### 2. Database Configuration

The application is configured to use:
- **Database Name**: `daamdb`
- **Username**: `root` (default)
- **Password**: `secret123` (default)
- **Port**: `3306` (default)

You can modify these settings in `application.properties`.

## Getting Started

### Method 1: Using Maven Wrapper (Recommended)

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd menu-api
   ```

2. **Make Maven wrapper executable** (Linux/Mac)
   ```bash
   chmod +x mvnw
   ```

3. **Install dependencies**
   ```bash
   ./mvnw dependency:resolve
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

### Method 2: Using Installed Maven

1. **Install dependencies**
   ```bash
   mvn dependency:resolve
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

### Method 3: Using JAR file

1. **Build the application**
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Run the JAR file**
   ```bash
   java -jar target/menu-api-*.jar
   ```

## Verifying Startup

Once started, you should see:
- Application running on `http://localhost:8080`
- H2 Console available at `http://localhost:8080/h2-console` (for development)
- Database tables created automatically
- Sample data loaded from `data.sql`

**Test the API:**
```bash
curl http://localhost:8080/api/menu_items
```

## API Endpoints

### Base URL
All endpoints are prefixed with: `http://localhost:8080/api`

### Users
- `GET /users` - Get all users
- `POST /users` - Create new user
- `GET /users/{id}` - Get user by ID
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user

### Menu Items
- `GET /menu_items` - Get all menu items
- `POST /menu_items` - Create new menu item
- `GET /menu_items/{id}` - Get menu item by ID
- `PUT /menu_items/{id}` - Update menu item
- `DELETE /menu_items/{id}` - Delete menu item

### Orders
- `GET /orders` - Get all orders
- `POST /orders` - Create new order
- `GET /orders/{id}` - Get order by ID
- `PUT /orders/{id}` - Update order
- `DELETE /orders/{id}` - Delete order
- `GET /orders/user/{userid}` - Get orders for specific user

### Order Items
- `GET /items` - Get all order items
- `GET /items/{id}` - Get order item by ID
- `PUT /items/{id}` - Update order item
- `DELETE /items/{id}` - Delete order item
- `GET /items/order/{orderid}` - Get items for specific order
- `POST /items/order/{orderid}` - Add items to order
- `DELETE /items/order/{orderid}` - Remove all items from order

### Films
- `GET /films` - Get all films
- `POST /films` - Create new film
- `GET /films/{id}` - Get film by ID
- `PUT /films/{id}` - Update film
- `DELETE /films/{id}` - Delete film

### Static Images
- `GET /images/food/{filename}` - Serve food images

## Testing

### Running Unit Tests
```bash
./mvnw test
```

### Using Postman
1. Import the provided Postman collections from `C:\LabFiles\capstone\Postman\`
2. Test individual endpoints
3. Verify HTTP status codes and response formats

### Manual Testing with cURL

**Get all menu items:**
```bash
curl -X GET http://localhost:8080/api/menu_items
```

**Create a new user:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "first": "Test",
    "last": "User",
    "email": "test@example.com",
    "phone": "(555) 123-4567",
    "roles": "ROLE_USER"
  }'
```

## Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration (H2 for development)
spring.datasource.url=jdbc:h2:mem:daamdb
spring.datasource.username=sa
spring.datasource.password=

# MySQL Configuration (production)
# spring.datasource.url=jdbc:mysql://localhost:3306/daamdb
# spring.datasource.username=root
# spring.datasource.password=secret123

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

### Switching to MySQL

To use MySQL instead of H2:

1. Uncomment MySQL configuration in `application.properties`
2. Comment out H2 configuration
3. Ensure MySQL is running and `daamdb` database exists
4. Restart the application

### Environment Variables

You can override default values using environment variables:
- `DB_HOST` - Database host (default: localhost)
- `DB_PORT` - Database port (default: 3306)
- `DB_NAME` - Database name (default: daamdb)
- `DB_USER` - Database username (default: root)
- `DB_PASSWORD` - Database password (default: secret123)

## Extra Features

### Authentication (Extra Mile)
- JWT-based authentication
- Role-based authorization (ADMIN, USER, SERVER)
- Secure endpoints with Bearer tokens

### Image Serving
- Static image serving for menu items
- Images accessible via `/images/food/{filename}`


## Frontend Integration

### Demo React Application

A React frontend is available at `C:\LabFiles\capstone\ReactApp\daamfe`:

1. **Navigate to frontend directory:**
   ```bash
   cd C:\LabFiles\capstone\ReactApp\daamfe
   ```

2. **Install dependencies:**
   ```bash
   pnpm install
   ```

3. **Start the frontend:**
   ```bash
   npm run dev
   ```

4. **Access the application:**
    - Frontend: `http://localhost:3000`
    - Backend API: `http://localhost:8080`

### Testing with Frontend

The React application provides:
- Menu browsing and ordering
- User registration and login
- Order history viewing
- Admin functionality (if logged in as admin)

**Default Login Credentials:**
- Username: `admin`
- Password: `pass`

## Troubleshooting

### Common Issues

**Port 8080 already in use:**
```bash
# Change port in application.properties
server.port=8081
```

**Database connection errors:**
- Verify MySQL is running
- Check database credentials
- Ensure `daamdb` database exists

**Maven dependency issues:**
```bash
./mvnw clean install
```

**Tests failing:**
```bash
./mvnw test -Dspring.profiles.active=test
```

### Logs and Debugging

Enable debug logging:
```properties
logging.level.com.simple.menu_api=DEBUG
logging.level.org.springframework.web=DEBUG
```



