package com.example.finaltest.backend.service;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.entity.Point;
import com.example.finaltest.backend.repository.PipeRepository;
import com.example.finaltest.backend.repository.PointRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PointService {
    private static final Logger LOGGER = Logger.getLogger(PointService.class.getName());
    private PointRepository pointRepository;
    private PipeRepository pipeRepository;

    public PointService(PointRepository pointRepository, PipeRepository pipeRepository) {
        this.pointRepository = pointRepository;
        this.pipeRepository = pipeRepository;
    }

    public List<Point> findAll() {
        return pointRepository.findAll();
    }

    public List<Point> findAll(String pipe) {
        if (pipe == null || pipe.isEmpty())
            return pointRepository.findAll();
        else
            return pointRepository.findAllByPipeName(pipe);
    }

    public long count() {
        return pointRepository.count();
    }

    public void delete(Point point) {
        pointRepository.delete(point);
    }

    public void save(Point point) {
        if (point == null) {
            LOGGER.log(Level.SEVERE, "Point is null!!!!!");
            return;
        }
        pointRepository.save(point);
    }

    @PostConstruct
    public void populateTestData() {
        if (pipeRepository.count() == 0) {
            pipeRepository.saveAll(
                    Stream.of("FirstPipe", "SecondPipe", "ThirdPipe")
                    .map(Pipe::new)
                    .collect(Collectors.toList())
            );
        }

        if (pointRepository.count() == 0) {
            Random r = new Random(0);
            List<Pipe> pipes = pipeRepository.findAll();
            pointRepository.saveAll(
                    Stream.of("1.0 3.0 5.2", "9.8 3.5 5.2", "7.8 6.2 9.2", "2.0 3.0 5.2",
                            "7.8 2.3 9.2", "3.0 8.0 5.2", "6.0 0.0 5.2", "7.0 6.0 6.2",
                            "2.5 5.0 5.2", "1.0 3.4 5.2", "1.0 3.0 7.2", "6.0 3.0 5.2")
                    .map(point -> {
                        String[] points = point.split(" ");
                        Point p = new Point();
                        p.setX(Double.parseDouble(points[0]));
                        p.setY(Double.parseDouble(points[1]));
                        p.setZ(Double.parseDouble(points[2]));
                        p.setPipe(pipes.get(r.nextInt(pipes.size())));
                        return p;
                    }).collect(Collectors.toList())
            );
        }
    }
}
