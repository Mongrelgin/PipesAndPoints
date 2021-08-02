package com.example.finaltest.backend.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Point extends AbstractEntity implements Cloneable {
    @NotNull
    @NotEmpty
    private Double x = 0.0;

    @NotNull
    @NotEmpty
    private Double y = 0.0;

    @NotNull
    @NotEmpty
    private Double z = 0.0;

    @ManyToOne
    @JoinColumn(name = "pipe_id")
    private Pipe pipe;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
