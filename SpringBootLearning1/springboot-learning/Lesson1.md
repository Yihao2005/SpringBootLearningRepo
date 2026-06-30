# What is Spring Boot?
Spring boot is a java backend framework that:
- Builds web application quickly
- Auto - Configures complex set up
- Includes an embedded server (Tomcat)
- Lets you run REST APIs directly

Core idea: "less configuration, more development"

# What can Spring Boot Do
Frontend -> Backend -> Database

- Authentication systems
- REST APIs
- Database operations
- Microservices

# Project structure
src/main/java
└── com.example.demo
├── controller
├── service
├── repository
└── entity

| Layer | Role | 
| controller | Handles requests | 
| service | business logic |
| repository | database access |
| entity | data model |

# First Spring Boot Project
```java
@SpringBootApplication
public class main DemoApplication {
    public static void main(String[] args){
        SpringApplication.run(main.java.com.example.demo.DemoApplication.class, args);
    }
}
```
@SpringBootApplication = marks entry point
run() = start embedded server

# First REST API
```java
@RestController
public class main HelloController{

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot";
    }
}
```
@RestController -> Return data directly
@GetMappinh -> handle GET request
/hello -> URL path

# How request flows
Browser → /hello request
↓
Spring Boot
↓
main.java.com.example.demo.controller.HelloController.hello()
↓
Return response

Request -> Controller method -> Response

# REST API Basics
| Method | Purpose |
| GET | Read data |
| POST | Create data |
| PUT | Update data |
| DELETE | Delete data |




