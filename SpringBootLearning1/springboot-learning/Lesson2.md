# Chapter 2: Receiving Data from the frontend

```java
import org.springframework.web.bind.annotation.GetMapping;

@GetMapping("/hello")
public String hello(){
    return "Hello Spring Boot";
}
```
The browser visits a fixed URL, and the backend returns fixed content.

But in real project, the frontend needs to send data to the backend, such as a user's name, id, or search keyword.

# Part 1: @RequestParam Query Parameters 
@RequestParam is used to receive parameters after ? in a URL.

```java
 @GetMapping("/greet")
    public String greet(@RequestParam String name){
        return "Hello, " + name + " !";
    }
```
Get the parameter called from the URL and store it in the Java variable name

## Test
```URL
http://localhost:8080/greet?name=Tom
```

# Part 2: Multiple Query Parameters
Real APIs often receive more than one parameter.

```java
  @GetMapping("/student")
    public String student(
            @RequestParam String name,
            @RequestParam int age
    ){
        return name + " is " + age + " years old.";
    }
```

## Test
```URL
 http://localhost:8080/student?name=YY&age=10
```
& means "send another parameters".

# Part 3: @PathVariable Path Variables 

## What does It do?
@PathVariable receives dynamic values directly from the URL path.
For example:
```URL
 http://localhost:8080/users/1
```
Here, 1 is the user id.

```java

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id){
        return "User id is: "+ id;
    }
```
{id} is a dynamic Part of the URL. @PathVariable is used to extract {id} from the URL and store it in the Java variable
id.

## Test
```URL
 http://localhost:8080/users/1
```

# Difference between @RequestParam and @PathVariable

| Annotation | Example | Typical use |
| @RequestParam | /users?city=Melbourne | Search, Filter, Pagination
| @PathVariable | /users/1 | Identify one specific resource

Get /users/1 : Get users whose id is one.
Get /users?city=Melbourne : Find all users living in Melbourne.
