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

# Part 2: POST, JSON, and @RequestBody

## What is POST?
GET is usually used to retrieve data.

POST is usually used to create or submit data.

Example:
- Registering an account 
- Creating a task
- Publishing a post
- Submitting login information

## Create a class to retrieve data
**dto** : Data Transfer Object

dto is an object used to transfer data from backend and frontend

```java
public class CreateUserRequest {
    private String name;
    private int age;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

## Why do we need getters and setters
 SpringBoot receives JSON and needs to put the values into a java object

## Create a POST API
```java

    @PostMapping("/users")
    public String createUser(@RequestBody CreateUserRequest request){
        return "User Created: "
                + request.getName()
                + " age: "
                + request.getAge()
                + " email: "
                +request.getEmail();
    }
```

## @RequestBody
Convert the JSON sent by the frontend into a CreateUserRequest java object

Frontend sends JSON
↓
@RequestBody
↓
CreateUserRequest object
↓
request.getName()
request.getAge()
request.getEmail()

## Testing a POST request with curl

Browser address bar are good for testing GET requests, but they are not suitable
for sending JSON POST request.

curl is command-line tool for sending network requests.

```Terminal
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Yihao",
    "age": 20,
    "email": "yuyihao@gmail.com"
  }'
```

```Terminal
 -X POST
```
Specify the request method is POST

```Terminal
  http://localhost:8080/users
```
The URL of your Spring Boot API

```Terminal
 -H "Content-Type: application/json"
```
Tell the backend that the request body uses JSON format.

```Terminal
 -d '{
    "name": "Yihao",
    "age": 20,
    "email": "yuyihao@gmail.com"
  }'
```
-d means the data being sent, also called the request body.