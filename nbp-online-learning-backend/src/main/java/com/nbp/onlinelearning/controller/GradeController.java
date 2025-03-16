package com.nbp.onlinelearning.controller;

import com.nbp.onlinelearning.model.Grade;
import com.nbp.onlinelearning.sevice.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/grades")
public class GradeController {
    @Autowired
    GradeService gradeService;
    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<Grade> getGradeByDescription(@PathVariable String description) {
        Grade grade = gradeService.findGradeByDescription(description);
        if (grade != null) {
            return new ResponseEntity<>(grade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/value/{value}")
    public ResponseEntity<Grade> getGradeByValue(@PathVariable long value) {
        Grade grade = gradeService.findGradeByValue(value);
        if (grade != null) {
            return new ResponseEntity<>(grade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
