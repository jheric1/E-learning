package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.model.*;
import com.nbp.onlinelearning.repository.QuestionRepository;
import com.nbp.onlinelearning.repository.QuizRepository;
import com.nbp.onlinelearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;


    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAllQuizzes();
    }

    public Quiz getQuizById(long id) {
        return quizRepository.findQuizById(id);
    }

    public Quiz createQuiz(CreateQuizDTO createQuizDTO) {
        int quizId = quizRepository.createQuiz(createQuizDTO.getQuiz());

        for (Question question : createQuizDTO.getQuestions()) {
            question.setQuizId(quizId);
            int questionId = questionRepository.createQuestion(question);
            for (AnswerOption answerOption : question.getAnswerOptions()) {
                questionRepository.insertAnswerOption(answerOption.getAnswerData(), questionId, answerOption.isCorrect());
            }
        }

        List<User> students = userRepository.findAllStudentsFromCourse(createQuizDTO.getQuiz().getCourseId());
        for (User student : students) {
            quizRepository.assignQuiz(quizId, student.getId());
        }
        return createQuizDTO.getQuiz();
    }

    public void submitQuizAssignment(long quizAssignmentId, List<Answer> answerList, long studentId){
        quizRepository.submitQuizAssignment(quizAssignmentId);
        long quizId = quizRepository.findQuizByAssignmentId(quizAssignmentId);
        QuizAssignment quizAssignment = getQuizAssignment(studentId, quizId);

        double totalPoints = 0;
        for(Answer answer : answerList){
            questionRepository.submitAnswer(quizAssignmentId, answer);

            Question question = quizAssignment.getQuiz().getQuestions().stream().filter(q -> q.getId() == answer.getQuestionId()).toList().get(0);
            AnswerOption correctOption = question.getAnswerOptions().stream().filter(AnswerOption::isCorrect).toList().get(0);
            if(Objects.equals(correctOption.getAnswerData(), answer.getAnswerData())){
                totalPoints = totalPoints + question.getMaxPoints();
            }
        }

        quizRepository.gradeQuiz(quizAssignmentId, totalPoints);
    }

    public QuizAssignment getQuizAssignment(long studentId, long quizId) {
        QuizAssignment quizAssignment = quizRepository.getQuizAssignment(studentId, quizId);

        CreateQuizDTO quizDto = new CreateQuizDTO();
        quizDto.setQuiz(getQuizById(quizId));

        List<Question> questions = questionRepository.findAllQuestionForTheQuiz(quizId);
        for (Question question : questions) {
            List<AnswerOption> answerOptionList = questionRepository.getAnswerOptions(question.getId());
            question.setAnswerOptions(answerOptionList);
        }

        List<Answer> answerList = questionRepository.getAnswersForQuiz(quizAssignment.getId());
        quizAssignment.setAnswerList(answerList);
        quizDto.setQuestions(questions);
        quizAssignment.setQuiz(quizDto);

        return quizAssignment;
    }

    public boolean deleteQuiz(long id) {
        if (id == 0) {
            return false;
            //TODO SAME AS ABOVE
        }

        return quizRepository.deleteQuiz(id);
    }

    public List<QuizResultDto> getQuizResults(long quizId){
        return quizRepository.getQuizResults(quizId);
    }

}
