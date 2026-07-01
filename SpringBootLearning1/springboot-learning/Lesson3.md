# Chapter 3: Returning JSON Objects
```JSON
{
  "title": "study",
  "description": "study for one hour",
  "priority": 1
}
```
JSON is the most common data format returned to frontend
applications.

## Create TaskResponse
```java
public class TaskResponse {

    private String title;
    private String description;
    private int priority;

    public TaskResponse(String title,String description, int priority){
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getPriority(){
        return priority;
    }
}
```
This class represents the task data returned from backend to frontend.

## Update /tasks API

Previous code:
```java
@PostMapping("/tasks")
    public String createTasks(@RequestBody CreateTaskRequest request){
        return "Task created: "
                + request.getTitle()
                + " Description: "
                + request.getDescription()
                + " Priority: "
                + request.getPriority();
    }
```

After update:
```java
    @PostMapping("/tasks")
    public TaskResponse createTasks(@RequestBody CreateTaskRequest request){
        return new TaskResponse(
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
                );
    }
```

## Why it automatically Converted into JSON?
Spring Boot automatically converts Java object into JSON because of the @RestController.

Java TaskResponse object
↓
Spring Boot
↓
JSON response
↓
curl / frontend receives JSON

## Test with curl
```Terminal
curl -X POST http://localhost:8080/tasks \
-H "Content-Type:application/json" \
-d '{
  "title": "study",
  "description": "study for one hour",
  "priority": 1
  }'
```

or pretty JSON output
```Bash
curl -X POST http://localhost:8080/tasks \
-H "Content-Type:application/json" \
-d '{
  "title": "study",
  "description": "study for one hour",
  "priority": 1
  }' | python3 -m json.tool
```

## KEY POINTS
The frontend sends JSON, Spring Boot converts it into a Java Object, and the Spring Boot converts it back to JSON.