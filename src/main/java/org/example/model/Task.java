package org.example.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long topicId;
    private TaskStatus status;
    private final LocalDateTime createdAT;
    private LocalDateTime deadline;

    public Task(Long id,String title,String description,Long topicId,LocalDateTime deadline){

        this.id=id;
        this.title = resolveTitle(title);
        this.description=description;
        this.topicId=topicId;
        this.status=TaskStatus.TODO;
        this.createdAT=LocalDateTime.now();
        this.deadline=deadline;
    }
    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Long getTopicId(){
        return  topicId;
    }

    public TaskStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAT(){
        return createdAT;
    }
    public LocalDateTime getDeadline(){
        return deadline;
    }
    private String resolveTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return title;
    }
    public void setId(Long id){
        this.id=id;
    }
    public void setTitle(String title){

        this.title=resolveTitle(title);
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(TaskStatus status){
        this.status=status;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o){
        if(this==o)return true;
        if(o==null||getClass()!=o.getClass())return false;
        Task task = (Task) o;
        return Objects.equals(id,task.id);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
    @Override
    public String toString(){
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", deadline=" + deadline +
                '}';
    }
}
