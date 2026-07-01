package com.example.demo.dto;

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
