package com.example.demo.service;

import com.example.demo.dto.CreateTaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.dto.UpdateTaskRequest;
import com.example.demo.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<TaskResponse> getTasksByPriority(int priority){
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task: tasks){
            if (task.getPriority() == priority){
                responses.add(convertToResponse(task));
            }
        }
        return responses;
    }

    public TaskResponse updateTask(long id, UpdateTaskRequest request){
        for (Task task: tasks){
            if (task.getId() == id){
                task.setTitle(request.getTitle());
                task.setDescripton(request.getDescription());
                task.setPriority(request.getPriority());
                return convertToResponse(task);
            }
        }
        return null;
    }

    public boolean deleteTask(long id){
        for (Task task: tasks){
            if (task.getId() == id){
                tasks.remove(task);
                return true;
            }
        }
        return false;
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
