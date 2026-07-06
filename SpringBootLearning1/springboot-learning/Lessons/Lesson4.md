# Chapter 4: From APIs to a Simple Task System

Controller → Service → Data

The Controller handles requests; The Service contains business logic; 
A List temporarily stores data.

## Part 1: Final Goal 
POST /tasks             Create a task

GET  /tasks             Get all tasks 

GET  /tasks/{id}         Get one task

## Part 2: Why Not Put Everything in the Controller?

Client / curl

↓

Controller

↓

Service

↓

List / Database

| Layer | Responsibility |
| Controller | Receives requests and return responses |
| Service | Business logic |
| Repository | Database access, learned later |
| DTO | API transfer object |

## Part 3: Create the Task Class
```java
public class Task {

    private Long id;
    private String title;
    private String descripton;
    private int priority;

    public Task (Long id, String title, String descripton, int priority){
        this.id = id;
        this.title = title;
        this.descripton = descripton;
        this.priority = priority;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescripton(){
        return  descripton;
    }

    public int getPriority(){
        return  priority;
    }
}
```
Task is the actual task object stored internally by the backend.

## Part 4: Upgrade TaskResponse

Previous Code:
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

After Update:
```java
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private int priority;

    public TaskResponse(String title,String description, int priority){
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public Long getId(){
        return id;
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
The response now include ID, because the backend usually tells the client the new task identifier

## Part 5:Create TaskService
```java

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public TaskResponse createTask(CreateTaskRequest request){
        Task task = new Task(
                nextId,
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
        );
        nextId++;
        tasks.add(task);

        return convertToResponse(task);
    }

    public List<TaskResponse> getAllTasks(){
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task: tasks){
            responses.add(convertToResponse(task));
        }

        return  responses;
    }

    public TaskResponse getTaskById(long id){
        for (Task task: tasks){
            if (task.getId() == id){
                return convertToResponse(task);
            }
        }

        return null;
    }

    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescripton(),
                task.getPriority()
        );
    }

}
```

## Part 6: Understand @Service

**@Service** tells Spring Boot that this class is a business-logic component managed by Spring

```java
private final List<Task> tasks = new ArrayList<>();
private long nextId = 1;
```

tasks = temporary task storage
nextId = auto-generated task id

They only exist in memory; they are not in a database yet.

## Part 7: Update the Controller
In the HelloController.java, we add:
```java
    private final TaskService taskService;

    public HelloController(TaskService taskService){
        this.taskService = taskService;
    }
```

SpringBoot creates the TaskServices and gives it to the Controller.

## Part 8: Test with curl
```Bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "study",
    "description": "study for one hour",
    "priority": 1
  }'
```

Get all tasks:
```URL
 http://localhost:8080/tasks
```

Get task with id 1 
```URL
 http://localhost:8080/tasks/1
```

Get task with priority 1
```URL
 http://localhost:8080/tasks/search?priority=1
```