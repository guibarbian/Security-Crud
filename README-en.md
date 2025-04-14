
# Crud-Security 🚷👮

## Language 📄
This README is also available in other languages:
- 🇧🇷 [Portuguese](README.md)

## The Project 📊

This is a simple task manager crud with security implemented using JWT.

## Used Technologies 🧭

- **SpringBoot** - Main Framework
- **Spring Data JPA** - For Database interactions
- **H2 Database** - In-memory database for development and testing
- **Spring Security** - For authentication and authorization
- **Swagger** - For API documentation
- **Maven** - Dependency management
- **JUnit** - For Unit Tests
- **MockMVC** - For Integration tests

## Requirements 📋

- Java 8 or superior
- Maven
- IDE(recommended IntelliJ or VSCode)

## How to run project

1. Copy the repository
```bash
git clone https://github.com/guibarbian/Security-Crud
cd Security-Crud
```
2. Install dependencies
```bash
mvn install
```
3. Run the application
```bash
mvn spring-boot:run
```
The app will be running on http://localhost:8080

You can use some API client like Postman or Insomnia to test the endpoints manually manualmente

# Endpoints

## Security

This API has a security pattern based on JWT (JSON Web Token) for authentication and authorization. To use its 
endpoints, you must register an user and log with the asked credentials(email and password).

To create an user, just access the endpoint `auth/registration` with a JSON body containing the following info:

```json
{
  "name": "usersName",
  "email": "usersEmail",
  "password": "usersPassword"
}
```
After you create your user(Endpoint has returned 201), you can access `auth/login` to authenticate and utilize 
the other endpoints. The authentication requires a JSON body with the following info:

```json
{
  "email": "usersEmail",
  "password": "usersPassword"
}
```

If the API returns 200, means that you're now authenticated, copy the returned token and paste it in the 
`Authentication` option to access the `tasks` endpoints.

## Tasks

This API has the following endpoints for task management:

| Method | Endpoint      | Description        |
|--------|---------------|--------------------|
| GET    | `/tasks/{id}` | Finds a task by ID |
| POST   | `/tasks`      | Creates a new task |
| PUT    | `/tasks/{id}` | Updates a task     |
| DELETE | `/tasks/{id}` | Deletes a task     |

## 
To create or update a task, you must send a JSON Body with the following attributes:


```json
{
  "name": "taskName",
  "description": "taskDescription",
  "status": "taskStatus"
}
```

# Developed with ⚙

- **IntelliJ IDEA**

# Author ✏

- Guilherme A. Barbian 

