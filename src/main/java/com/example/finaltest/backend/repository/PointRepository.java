package com.example.finaltest.backend.repository;

import com.example.finaltest.backend.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    //@Query
    List<Point> findAllByPipeName(String name);
}
