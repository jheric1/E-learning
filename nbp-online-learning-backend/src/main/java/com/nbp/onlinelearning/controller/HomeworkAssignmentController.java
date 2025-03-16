package com.nbp.onlinelearning.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.nbp.onlinelearning.model.GradeHomeworkDto;
import com.nbp.onlinelearning.model.HomeworkAssignment;
import com.nbp.onlinelearning.model.HomeworkAssignmentDto;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.sevice.HomeworkAssignmentService;
import com.nbp.onlinelearning.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/homework-assignment")
public class HomeworkAssignmentController {
    @Autowired
    HomeworkAssignmentService homeworkAssignmentService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<HomeworkAssignment>> findAllHomeworkAssignments() {
        List<HomeworkAssignment> homeworkAssignments = homeworkAssignmentService.getAllHomeworkAssignments();
        return new ResponseEntity<>(homeworkAssignments, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/{homeworkId}")
    public HomeworkAssignment getStudentAssignment(@PathVariable long homeworkId){
        User user = userService.getUserFromToken();
        return homeworkAssignmentService.findHomeworkAssignmentByHomeworkId(homeworkId, user.getId());
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/submit")
    public HomeworkAssignment submitHomeworkAssignment(@RequestBody HomeworkAssignment homeworkAssignment){
        return homeworkAssignmentService.submitHomework(homeworkAssignment);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping("/homework-results/{homeworkId}")
    public List<HomeworkAssignmentDto> getHomeworkResults(@PathVariable long homeworkId){
        return homeworkAssignmentService.findHomeworkAssignmentsByHomeworkId(homeworkId);
    }



    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping("/grade-homework")
    public void gradeHomework(@RequestBody GradeHomeworkDto gradeHomeworkDto){
        homeworkAssignmentService.gradeHomework(gradeHomeworkDto);
    }




}