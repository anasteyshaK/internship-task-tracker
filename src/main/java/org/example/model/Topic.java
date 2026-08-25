package org.example.model;

import java.util.Objects;

public class Topic {
    private Long id;
    private String name;

    public Topic(Long id,String name){

        this.id = id;
        this.name = resolveName(name);
    }

    public Long getId() {
        return id;
    }
    public String getName(){
        return name;
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

    @Override
    public boolean equals(Object o){
        if(this==o)return true;
        if(o==null ||getClass()!=o.getClass())return false;
        Topic topic = (Topic) o;
        return Objects.equals(id,topic.id);
    }
    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
    @Override
    public String toString(){
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

