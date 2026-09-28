package org.example.model;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "title", "status", "deadline"})
public class Task {
    private Long id;
    private String title;
    @Setter
    private String description;
    private Long topicId;
    @Setter
    private TaskStatus status;
    private final LocalDateTime createdAT;
    @Setter
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


}
