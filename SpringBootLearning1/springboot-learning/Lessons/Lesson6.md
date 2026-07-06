# Chapter 6: Professional API Responses

## What is Http Status Code?
A status code tells the frontend whether a request succeeded, failed, or 
could not find the resource.

| Status | Meaning|

| 200 OK | Request Succeeded |

| 201 Created | New resource Created |

| 400 Bad Request | Request format is invalid |

| 404 Not Found | Resource doesn't exist |

| 204 No Content | Succeed, no response body ｜

## What is ResponseEntity?
ResponseEntity lets you control both response and HTTP status code.

Service finds data. Controller decides http code.

## Update Get-One-Task Endpoint

Previous Code: 
```java
 @GetMapping("/tasks/{id}")
    public TaskResponse getTaskById(@PathVariable long id){
        return taskService.getTaskById(id);
    }
```

Updated Code:
```java
@GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable long id){
        TaskResponse task = taskService.getTaskById(id);

        if (task == null){
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }
```

## Line-By-Line Explaination
```java
public ResponseEntity<TaskResponse> getTaskById(@PathVariable long id);
```
This Endpoint now returns "status code + TaskResponse", not just a TaskResponse.

```java
TaskResponse task = taskService.getTaskById(id);
```
Ask the Service to find the task First.

`
if (task == null)
`
If no task exists with that id.

`
return ResponseEntity.notFound().build();
`
404 Not Found

`
return ResponseEntity.ok(task);
`
200 OK

## Update Create Endpoint

Previous Code
```java
@PostMapping("/tasks")
public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
    return taskService.createTask(request);
}
```

Updated Code
```java
@PostMapping("/tasks")
public ResponseEntity<TaskResponse> createTask(
        @RequestBody CreateTaskRequest request
) {
    TaskResponse task = taskService.createTask(request);

    return ResponseEntity.status(201).body(task);
}
```

More Professional Way:
`return ResponseEntity.status(HttpStatus.CREATED).body(task);`

When a task is created, return task JSON with 201 created. 

## Update PUT Endpoint

Previous Code
```java
@PutMapping("/tasks/{id}")
public TaskResponse updateTask(
        @PathVariable long id,
        @RequestBody UpdateTaskRequest request
) {
    return taskService.updateTask(id, request);
}
```

Updated Code
```java
@PutMapping("/tasks/{id}")
public ResponseEntity<TaskResponse> updateTask(
        @PathVariable long id,
        @RequestBody UpdateTaskRequest request
) {
    TaskResponse updatedTask = taskService.updateTask(id, request);

    if (updatedTask == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(updatedTask);
}
```
## Update DELETE Endpoint

Previous Code
```java
@DeleteMapping("/tasks/{id}")
public String deleteTask(@PathVariable long id) {
    boolean deleted = taskService.deleteTask(id);

    if (deleted) {
        return "Task deleted successfully.";
    }

    return "Task not found.";
}
```

Updated Code
```java
@DeleteMapping("/tasks/{id}")
public ResponseEntity<Void> deleteTask(@PathVariable long id) {
    boolean deleted = taskService.deleteTask(id);

    if (!deleted) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
}
```
## Summary
| Status | Meaning| Code |

| 200 OK | Request Succeeded | ResponseEntity.ok(TaskResponse) |

| 201 Created | New resource Created | ResponseEntity.status(201).body(TaskResponse) |

| 400 Bad Request | Request format is invalid |

| 404 Not Found | Resource doesn't exist | ResponseEntity.notFound().build() |

| 204 No Content | Succeed, no response body ｜ ResponseEntity.noContent().build() |

## View Status Codes with curl

Post
```Bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "learn ResponseEntity",
    "description": "finish Chapter 6",
    "priority": 1
  }'
```

Get
```Bash
curl -i http://localhost:8080/tasks/999
```

Delete
```Bash
curl -i -X DELETE http://localhost:8080/tasks/1
```