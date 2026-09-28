package org.example.model;

import java.util.Objects;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode(of = "id")
@Getter
@ToString(of = {"id", "name"})
public class Topic {
    private Long id;
    private String name;

    public Topic(Long id,String name){

        this.id = id;
        this.name = resolveName(name);
    }
    private String resolveName(String name){
        return (name == null || name.isBlank()) ? "No name" : name;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = resolveName(name);
    }

}

