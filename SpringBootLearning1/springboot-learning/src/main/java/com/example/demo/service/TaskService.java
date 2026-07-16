package com.example.demo.service;

import com.example.demo.dto.CreateTaskRequest;
import com.example.demo.dto.TaskResponse;
import com.example.demo.dto.UpdateTaskRequest;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
