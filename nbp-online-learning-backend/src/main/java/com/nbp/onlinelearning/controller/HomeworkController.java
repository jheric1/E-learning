package com.nbp.onlinelearning.controller;

import com.nbp.onlinelearning.model.Homework;
import com.nbp.onlinelearning.sevice.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/homework")
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;

    @GetMapping("/all")
    public ResponseEntity<List<Homework>> findAllHomeworks() {
        List<Homework> homeworks = homeworkService.getAllHomeworks();
        return new ResponseEntity<>(homeworks, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Homework> findHomeworkById(@PathVariable long id) {
        Homework homework = homeworkService.getHomeworkById(id);
        if (homework != null) {
            return new ResponseEntity<>(homework, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/create-homework")
    public Homework createHomework(@RequestBody Homework homework){
        return homeworkService.createHomework(homework);
    }
}