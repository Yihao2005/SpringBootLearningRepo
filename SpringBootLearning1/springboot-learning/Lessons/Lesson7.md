# Validating User Input
## Part 1: Add the Validation Dependency

Open pom.xml

Add:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
Into dependencies.

Maven will download the validation-related dependencies.

If error occurs, enter into terminal:

```Bash
./mvnw dependency:purge-local-repository \
-DreResolve=true \
-Dinclude=org.springframework.boot:spring-boot-starter-validation
```

And

```Bash
./mvnw clean package
```

## Part 2: Add Rules to CreateTaskRequest

```java
public class CreateTaskRequest {

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @NotBlank(message = "Description must not be blank.")
    private String description;

    @Min(value = 1, message ="Priority must be at least 1.")
    private int priority;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return  description;
    }

    public void setDescription(String description){
        this.description =description;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }
    
}
```
## Part 3: UnderStand the Annotation

`@NotBlank`: The String could not be null, empty or whitespace only.

`@Min(1)`: The number must be at least 1.

`message = "..."`: The error message shown when validation fails.

## Part 4: Enable Validation in the Controller
```java
@PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTasks(
           @Valid @RequestBody CreateTaskRequest request
    ){
        TaskResponse task = taskService.createTask(request);

        return ResponseEntity.status(201).body(task);
    }
```

`@Valid` tell spring boot to validate CreateTaskRequest before entering the method

## Part 5: Validate Update Requests Too
```java
package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UpdateTaskRequest {

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @NotBlank(message = "Description must not be blank.")
    private String description;

    @Min(value = 1, message = "Priority must be at least 1.")
    private int priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
```

And then update the put endpoint
```java
@PutMapping("/tasks/{id}")
public ResponseEntity<TaskResponse> updateTask(
        @PathVariable long id,
        @Valid @RequestBody UpdateTaskRequest request
) {
    TaskResponse updatedTask = taskService.updateTask(id, request);

    if (updatedTask == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(updatedTask);
}
```

## Part 6: Test a valid request
```Bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Study validation",
    "description": "Finish Chapter 7",
    "priority": 1
  }'
```

## Part 7: Test a invalid request
```Bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "description": " ",
    "priority": 0
  }'
```
The request is valid, but the submitted data violates validation rules

## Part 8: Ideal Error Response
```JSON
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "title": "Title must not be blank.",
    "priority": "Priority must be at least 1."
  }
}
```

