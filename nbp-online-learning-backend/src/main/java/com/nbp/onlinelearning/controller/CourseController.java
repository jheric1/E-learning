package com.nbp.onlinelearning.controller;

import com.nbp.onlinelearning.model.Course;
import com.nbp.onlinelearning.model.CreateCourseDTO;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.sevice.CourseService;
import com.nbp.onlinelearning.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Course>> findAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Course> findCourseById(@PathVariable long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping("/instructor")
    public List<Course> findCoursesByInstructor() {
        User user = userService.getUserFromToken();
        return courseService.getCourseByInstructor(user.getId());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/create-course")
    public void createCourse(@RequestBody CreateCourseDTO c) {
         courseService.createCourse(c);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/apply-to-course/{courseId}")
    public void addStudentOnCourse(@PathVariable long courseId) {
        User user = userService.getUserFromToken();
        courseService.addStudentOnCourse(user.getId(), courseId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/quit-course/{courseId}")
    public void removeStudentFromCourse(@PathVariable long courseId) {
        User user = userService.getUserFromToken();
        courseService.removeStudentFromCourse(user.getId(), courseId);
    }

    @PostMapping("/add-professor/{courseId}/{professorId}")
    public void addProfessorOnCourse(@PathVariable long courseId, @PathVariable long professorId) {
        courseService.addProfessorOnCourse(courseId, professorId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/my-courses")
    public List<Course> findStudentCourses() {
        User user = userService.getUserFromToken();
        return courseService.findStudentCourses(user.getId());
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/free-courses")
    public List<Course> findFreeCourses() {
        User user = userService.getUserFromToken();
        return courseService.findStudentFreeCourses(user.getId());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/delete-course/{courseId}")
    public void deleteCourse(@PathVariable long courseId){
        courseService.deleteCourse(courseId);
    }


}