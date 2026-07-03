# Chapter 5: Updating and Deleting Tasks

## Part1: Why Do We need a New Request DTO?
It represents what the client wants the task to become.

## Part2: Create UpdateTaskRequest
```java
public class UpdateTaskRequest {
    private String title;
    private String description;
    private int priority;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getPriority(){
        return  priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }
}
```

## Part3: Make Task Mutable
```java

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescripton(String descripton){
        this.descripton = descripton;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }
```
Task initially created

↓

Service finds the task by id

↓

Setter changes its fields

↓

Return updated TaskResponse

Setters allow the object's internal data to be updated.

## Part4: Add Update Logic in the task Service

```java
public TaskResponse updateTask(long id, UpdateTaskRequest request) {
    for (Task task : tasks) {
        if (task.getId() == id) {
            task.setTitle(request.getTitle());
            task.setDescripton(request.getDescription());
            task.setPriority(request.getPriority());
            return convertToResponse(task);
        }
    }
    return null;
}
```

## Part5: Add Delete Logic in the task Service

```java
public boolean deleteTask(long id){
    for (Task task:tasks){
        if (task.getId()==id){
            tasks.remove(task);
            return true;
        }
    }
    return false;
}
```

true = task found and deleted
false = no task with this id

## Part6: Add PUT Endpoint in Controller
```java
    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable long id,
            @RequestBody UpdateTaskRequest request
    ){
        return taskService.updateTask(id,request);
    }
```
The URL determines which task to update; The JSON determines what it should become

## Part7: Add Delete Endpoint in Controller
```java
    @DeleteMap 
    public String deleteTask(long id){
        boolean deleted = taskService.deleteTask(id);
        if (deleted == true){
            return "Task is deleted successfully!";
        }
        return "Task not found!";
    }
```

## Part8: Test Updating a task
```bash
 curl -X PUT http://localhost:8080/tasks/1 \
-H "Content-Type:application/json" \
-d '{
  "title": "study Spring Boot for one hour",
  "description": "study Spring Boot for one hour",
  "priority": 4
  }'
```

## Part9: Test Deleting a task
```bash
 curl -X DELETE http://localhost:8080/tasks/1
```

### CURD
C Create

U Update

R Read

D Delete