package com.nbp.onlinelearning.controller;

import com.nbp.onlinelearning.model.Question;
import com.nbp.onlinelearning.sevice.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
//    @GetMapping("/quiz/{quizId}")
//    public ResponseEntity<List<Question>>getAllQuestionsForTheQuiz(@PathVariable long quizId){
//        List<Question> questions = questionService.findAllQuestionsForTheQuiz(quizId);
//        return new ResponseEntity<>(questions, HttpStatus.OK);
//    }
    @GetMapping("/{questionId}")
    public ResponseEntity<Question>getQuestionById(@PathVariable long questionId){
        Question question = questionService.findQuestionByID(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
    @DeleteMapping("/delete-question/{questionId}")
    public boolean deleteQuestion(@PathVariable long questionId){
        return  questionService.deleteQuestionById(questionId);
    }
//    @PostMapping("/create-question")
//    public ResponseEntity<Question> createQuestion(@RequestBody Question question){
//        Question q = questionService.createQuestion(question);
//        if(q!=null){
//            return new ResponseEntity<>(q, HttpStatus.CREATED);
//        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
