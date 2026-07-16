package com.example.demo.model;

import jakarta.persistence.*;

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
