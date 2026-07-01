package com.example.demo.controller;

import com.example.demo.dto.CreateTaskRequest;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.TaskResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

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

    @PostMapping("/tasks")
    public TaskResponse createTasks(@RequestBody CreateTaskRequest request){
        return new TaskResponse(
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
                );
    }
}
