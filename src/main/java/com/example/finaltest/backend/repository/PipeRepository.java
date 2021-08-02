package com.example.finaltest.backend.repository;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PipeRepository extends JpaRepository<Pipe, Long> {

}
