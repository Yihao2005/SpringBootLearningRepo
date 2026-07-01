package com.example.demo.model;

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
