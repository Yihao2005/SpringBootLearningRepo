package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateTaskRequest {

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @NotBlank(message = "Description must not be blank.")
    private String description;

    @Min(value = 1, message ="Priority must be at least 1.")
    private int priority;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return  description;
    }

    public void setDescription(String description){
        this.description =description;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }


}
