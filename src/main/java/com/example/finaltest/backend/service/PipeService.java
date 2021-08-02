package com.example.finaltest.backend.service;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.entity.Point;
import com.example.finaltest.backend.repository.PipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PipeService {
    private static final Logger LOGGER = Logger.getLogger(PipeService.class.getName());
    private PipeRepository repository;

    public PipeService(PipeRepository repository) {
        this.repository = repository;
    }

    public List<Pipe> findAll() {
        return repository.findAll();
    }

    public long count() {
        return repository.count();
    }

    public void delete(Pipe pipe) {
        repository.delete(pipe);
    }

    public void delete(Set<Pipe> pipes) {
        repository.deleteAll(pipes);
    }

    public void save(Pipe pipe) {
        if (pipe == null) {
            LOGGER.log(Level.SEVERE, "Point is null!!!!!");
            return;
        }
        repository.save(pipe);
    }
}
