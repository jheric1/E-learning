package com.nbp.onlinelearning.controller;

import com.nbp.onlinelearning.model.Lesson;
import com.nbp.onlinelearning.sevice.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    LessonService lessonService;
    @GetMapping("/all")
    public List<Lesson> findAllLessons() {
        return lessonService.findAllLessons();
    }

    @GetMapping("/lessons/{id}")
    public List<Lesson> findLessonsByCourseId(@PathVariable long id){ return lessonService.findLessonsByCourseId(id);}

    @GetMapping("/id/{id}")
    public Lesson findLessonById(@PathVariable long id){ return lessonService.findLessonsById(id);}
    @PostMapping("/create-lesson")
    public Lesson createLesson(@RequestBody Lesson lesson){
        return lessonService.createLesson(lesson);
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteLesson(@PathVariable long id){
        return lessonService.deleteLesson(id);
    }


}
