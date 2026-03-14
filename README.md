# Secure Todo Backend (Spring Boot + JWT)

A secure REST API for managing todos built with **Spring Boot**, **MongoDB**, and **JWT authentication**.

## Features
- User Registration
- Login with JWT authentication
- Create Todo
- Fetch only logged-in user's todos
- Mark Todo as completed
- Delete Todo (only by owner)
- Secure endpoints using Spring Security + JWT

## Tech Stack
- Java
- Spring Boot
- Spring Security
- JWT
- MongoDB
- Maven

## API Endpoints

### Authentication
POST /users/register  
POST /users/login

### Todos
POST /todos  
GET /todos/my  
PUT /todos/{id}/complete  
DELETE /todos/{id}

## Security
- JWT based authentication
- User specific access control
- Password encryption using BCrypt

## Future Improvements
- Swagger API documentation
- Docker deployment
- Pagination for todos
- 
