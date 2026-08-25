package org.example.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class StudySession {
    private Long id;
    private Long taskId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public StudySession(Long id,Long taskId,LocalDateTime startTime,LocalDateTime endTime){
    this.id=id;
    this.taskId=resolveTaskId(taskId);
    this.startTime=resolveStartTime(startTime);
    this.endTime=resolveEndTime(this.startTime,endTime);
    }
    private Long resolveTaskId(Long taskId){
        if (taskId == null) {
            throw new IllegalArgumentException("taskId cannot be null");
        }
        return taskId;
    }
    private LocalDateTime resolveStartTime(LocalDateTime startTime){
        if (startTime == null) {
            throw new IllegalArgumentException("startTime cannot be null");
        }
        return startTime;
    }
    private LocalDateTime resolveEndTime(LocalDateTime startTime,LocalDateTime endTime){
        if(endTime != null && !startTime.isBefore(endTime)){
            throw new IllegalArgumentException("endTime must be after startTime");
        }
        return endTime;
    }
    public Long getId(){
        return id;
    }
    public Long getTaskId(){
        return taskId;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }

    public void setId(Long id){
        this.id=id;
    }
    public void setTaskId(Long taskId){
        this.taskId=resolveTaskId(taskId);
    }
    public void setStartTime(LocalDateTime startTime){
        this.startTime=resolveStartTime(startTime);
    }
    public void setEndTime(LocalDateTime endTime){
        this.endTime=resolveEndTime(this.startTime,endTime);
    }
    public Optional<Duration> getDuration(){
        if(endTime == null){
            return Optional.empty();
        }
        return Optional.of(Duration.between(startTime,endTime));
    }
    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if(o==null || getClass() != o.getClass())return false;
        StudySession that = (StudySession) o;
        return Objects.equals(id,that.id);

    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
    @Override
    public String toString(){
        return "StudySession{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
