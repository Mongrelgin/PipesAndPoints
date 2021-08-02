package com.example.finaltest.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Pipe extends AbstractEntity {
    private String name;

    @OneToMany(mappedBy = "pipe", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Point> points = new LinkedList<>();

    public Pipe() {
    }

    public Pipe(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Point> getPoints() {
        return points;
    }
}
