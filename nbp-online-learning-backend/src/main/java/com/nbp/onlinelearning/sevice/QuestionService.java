package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.model.Question;
import com.nbp.onlinelearning.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
//    public List<Question> findAllQuestionsForTheQuiz(long quizId) {
//        return questionRepository.findAllQuestionForTheQuiz(quizId);
//    }

    public boolean deleteQuestionById(long questionId) {
        return questionRepository.deleteQuestionById(questionId);
    }

//    public Question createQuestion(Question question) {
//        return questionRepository.createQuestion(question);
//    }

    public Question findQuestionByID(long questionId) {
        return questionRepository.findQuestionById(questionId);
    }
}
