package com.example.demo.controller;

import com.example.demo.dto.CreateTaskRequest;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.dto.UpdateTaskRequest;
import com.example.demo.service.TaskService;
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

    @PostMapping("/tasks")
    public TaskResponse createTasks(@RequestBody CreateTaskRequest request){
        return taskService.createTask(request);
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public TaskResponse getTaskById(@PathVariable long id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/tasks/search")
    public List<TaskResponse> searchTasks(@RequestParam int priority){
        return taskService.getTasksByPriority(priority);
    }

    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable long id,
            @RequestBody UpdateTaskRequest request
    ){
        return taskService.updateTask(id,request);
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(
            @PathVariable long id
    ){
        boolean deleted = taskService.deleteTask(id);

        if (deleted){
            return "Task deleted successfully!";
        }

        return "Task not found!";
    }
}
