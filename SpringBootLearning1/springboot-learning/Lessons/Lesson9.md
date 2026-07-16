# Persisting Tasks in a Database
## Part 1: Add Database Dependencies

In pom.xml, add new dependencies:
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
```
And then Maven -> Reload all Maven Projects.

JPA (Java persistence API) maps Java objects to database tables, while H2 is the database used in this chapter

## Part 2: Configure the H2 Database
In src/main/resources/application.properties,
add:
```properties
spring.datasource.url=jdbc:h2:file:./data/tasksdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```


### Configuration Explanation
```properties
spring.database.url=jdbc:h2:file:./data/tasksdb
```
Store the database in a data directory under the project root.

```properties
spring.jpa.hibernate.ddl-auto=update
```
Automatically create or update database tables from entity classes.
```properties
spring.jpa.show-sql=true
```
Display SQL statements executed by Hibernate

## Part 3:Convert Task into a Database Entity
```java
@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String descripton;
    private int priority;

    public Task(){
    }

    public Task (String title, String descripton, int priority){
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

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescripton(){
        return  descripton;
    }

    public void setDescripton(String descripton){
        this.descripton = descripton;
    }

    public int getPriority(){
        return  priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }
}
```

## Part 4: Understand Entity Annotations

`@Entity`: Tell JPA that this class should be mapped to a database table.

`@Table(name="tasks")`: Set the database table name to tasks.

`@Id`:This filed is the table's primary key.

`@GeneratedValue(strategy = GeneratedType.IDENTITY)`: The database automatically generates the id.

## Part 5: Why Is a No-Argument Constructor Required?
```java
public Task(){
}
```
JPA needs no-argument constructor so it can create an empty object before populating its fields from a database row.

## Part 6: Create TaskRepository
```java
public interface TaskRespository extends JpaRepository<Task, Long> {
}
```
Although the interface is empty, Spring Data JPA automatically provides common CURD operations.

## Part 7: What Does the Repository Provide?
`taskRepository.save(task);`: save or update the data
`taskRepository.findAll();`: find all data
`taskRepository.findById(id);`: find by id
`taskRepository.deleteById(id);`: delete by id
`taskRepository.existsById();`: tell if exists by id

## Part 8: Replace the In-Memory List
```java
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(CreateTaskRequest request){
        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getPriority()
        );

        Task savedTask = taskRepository.save(task);

        return convertToResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task: tasks){
            responses.add(convertToResponse(task));
        }

        return  responses;
    }

    public TaskResponse getTaskById(long id){
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            return null;
        }

        return convertToResponse(taskOptional.get());
    }

    public List<TaskResponse> getTasksByPriority(int priority){
        List<TaskResponse> responses = new ArrayList<>();
        List<Task> tasks = taskRepository.findAll();

        for (Task task: tasks){
            if (task.getPriority() == priority){
                responses.add(convertToResponse(task));
            }
        }
        return responses;
    }

    public TaskResponse updateTask(long id, UpdateTaskRequest request){
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()){
            return null;
        }

        Task task = taskOptional.get();
        task.setTitle(request.getTitle());
        task.setDescripton(request.getDescription());
        task.setPriority(request.getPriority());

        Task updatedTask = taskRepository.save(task);

        return convertToResponse(updatedTask);
    }

    public boolean deleteTask(long id){
        if (!taskRepository.existsById(id)){
            return false;
        }

        taskRepository.deleteById(id);
        return true;
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
## Part 9: Understand Optional<Task>
The method `taskRepository.findById(id)` would not return Task directly,
but return `Optional<Task>` since the task might exist or not.

`Optional<Task> taskOptional = taskRepository.findById(id);`

if it exists:
`taskOptional.get()`

if it doesn't exist:
`taskOptional.isEmpty()`

```
Optional<Task>
├── contains Task
└── contains nothing
```

## Part 10: Test Create a Task 
```Bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn JPA",
    "description": "Finish Chapter 9",
    "priority": 1
  }'
```

## Part 11: Verify Persistence
```Bash
curl http://localhost:8080/tasks | python3 -m json.tool
```
is used to make sure that task exists. And then Rerun the SpringBoot and test again:
```Bash
curl http://localhost:8080/tasks | python3 -m json.tool
```

## Part 12: Updated Project Structure
```
com.example.demo
├── controller
│   └── HelloController.java
├── dto
│   ├── CreateTaskRequest.java
│   ├── UpdateTaskRequest.java
│   └── TaskResponse.java
├── exception
│   └── GlobalExceptionHandler.java
├── model
│   └── Task.java
├── repository
│   └── TaskRepository.java
├── service
│   └── TaskService.java
└── SpringbootLearningApplication.java
```
and the process has already been:
```plain text
curl
  ↓
Controller
  ↓
Service
  ↓
TaskRepository
  ↓
JPA / Hibernate
  ↓
H2 database 
```