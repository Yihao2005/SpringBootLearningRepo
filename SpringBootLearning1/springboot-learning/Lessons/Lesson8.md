# Global Exception Handling

## Part 1: Goal
When the client submit wrong tasks:
```json
{
  "title": "",
  "description": " ",
  "priority": 0
}
```

We expect to return:
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "title": "Title must not be blank.",
    "description": "Description must not be blank.",
    "priority": "Priority must be at least 1."
  }
}
```

## Part 2: Create an Exception Package
com.example.demo
├── controller
├── dto
├── model
├── service
└── exception

## Part 3: Create GlobalExceptionHandler
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException exception
    ){
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),error.getDefaultMessage())
                );
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 400);
        response.put("message", "Validation failed");
        response.put("errors",errors);
        
        return ResponseEntity.badRequest().body(response);
    }
}
```

## Part 4: Key Annotation Explanation
`@RestControllerAdvice`: This is a global controller helper that can handle exceptions from controllers in one place.

`@ExceptionHandler(MethodArgumentNotValidException.class)`: When @Valid validation fails, Spring Boot throws this exception; this method handles it.

## Part 5: Understand How Errors Are Extracted
`exception.getBindingResult().getFieldErrors()`: Get all validation errors for individual fields.

For example:

`errors.put(error.getField(),error.getDefaultMessage());`
will save them as:
```json
{
  "title": "Title must not be blank.",
  "priority": "Priority must be at least 1."
}
```
## Part 6: Test Invalid Input
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "description": " ",
    "priority": 0
  }' | python3 -m json.tool
```
## Part 7: Test Valid Input
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Study exception handling",
    "description": "Finish Chapter 8",
    "priority": 1
  }' | python3 -m json.tool
```
## Part 8: Why Use Global Exception Handling?
When there has no Global Exception Handling, each Controller method need to write lots of exception handling

When there has Global Exception Handling, Controller is only responsible for normal business logic, and the GlobalExceptionHandler is only responsible invalid format.