package com.example.demo.controller;

import com.example.demo.dto.CreateTaskRequest;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.dto.UpdateTaskRequest;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private final TaskService taskService;

    public HelloController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot!";
    }

    @GetMapping("/name")
    public String getName(){
        return "YourName";
    }

    @GetMapping("/school")
    public String getSchool(){
        return "YourSchool";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name){
        return "Hello, " + name + " !";
    }

    @GetMapping("/student")
    public String student(
            @RequestParam String name,
            @RequestParam int age
    ){
        return name + " is " + age + " years old.";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id){
        return "User id is: "+ id;
    }

    @GetMapping("/course")
    public String course(@RequestParam String name){
        return "Course: " + name;
    }

    @GetMapping("/book/{id}")
    public String book(@PathVariable int id){
        return "Book id: " + id;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody CreateUserRequest request){
        return "User Created: "
                + request.getName()
                + " age: "
                + request.getAge()
                + " email: "
                +request.getEmail();
    }


    //---------------------------------------------------------------------------------------

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTasks(
           @Valid @RequestBody CreateTaskRequest request
    ){
        TaskResponse task = taskService.createTask(request);

        return ResponseEntity.status(201).body(task);
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable long id){
        TaskResponse task = taskService.getTaskById(id);

        if (task == null){
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @GetMapping("/tasks/search")
    public List<TaskResponse> searchTasks(@RequestParam int priority){
        return taskService.getTasksByPriority(priority);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable long id,
            @Valid @RequestBody UpdateTaskRequest request
    ){
        TaskResponse updateTask = taskService.updateTask(id,request);

        if (updateTask == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable long id
    ){
        boolean deleted = taskService.deleteTask(id);

        if (!deleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
