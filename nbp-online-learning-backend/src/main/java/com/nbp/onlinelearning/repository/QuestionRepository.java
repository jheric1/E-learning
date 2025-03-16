package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.constants.QuestionType;
import com.nbp.onlinelearning.mapper.AnswerMapper;
import com.nbp.onlinelearning.mapper.AnswerOptionMapper;
import com.nbp.onlinelearning.mapper.QuestionMapper;
import com.nbp.onlinelearning.model.Answer;
import com.nbp.onlinelearning.model.AnswerOption;
import com.nbp.onlinelearning.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.nbp.onlinelearning.queries.QuestionQueries;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionRepository {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    QuestionTypeRepository questionTypeRepository;
    @Autowired
    AnswerOptionMapper answerOptionMapper;

    @Autowired
    AnswerMapper answerMapper;

    public List<Question> findAllQuestionForTheQuiz(long quizId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizId", quizId);
        return namedParameterJdbcTemplate.query(QuestionQueries.SELECT_ALL_QUESTIONS_FOR_QUIZ.getQuery(), argMap, questionMapper);
    }

    public List<AnswerOption> getAnswerOptions(long questionId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("questionId", questionId);
        return namedParameterJdbcTemplate.query(QuestionQueries.SELECT_ALL_QUESTION_ANSWER_OPTIONS.getQuery(), argMap, answerOptionMapper);
    }

    public List<Answer> getAnswersForQuiz(long quizAssignmentId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizAssignmentId", quizAssignmentId);

        return namedParameterJdbcTemplate.query(QuestionQueries.SELECT_ALL_QUIZ_ASSIGNMENT_ANSWERS.getQuery(), argMap, answerMapper);
    }

    public boolean deleteQuestionById(long questionId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", questionId);

        namedParameterJdbcTemplate.update(QuestionQueries.DELETE_QUESTION.getQuery(), argMap);
        return true;
    }

    public Integer createQuestion(Question question) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("text", question.getText());
        mapSqlParameterSource.addValue("max_points", question.getMaxPoints());
        mapSqlParameterSource.addValue("quiz_id", question.getQuizId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QuestionQueries.INSERT_QUESTION.getQuery(), mapSqlParameterSource, keyHolder, new String[]{"id"});
        return keyHolder.getKeyAs(BigDecimal.class).intValueExact();

    }

    public void insertAnswerOption(String answerData, long questionId, boolean isCorrect) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("questionId", questionId);
        argMap.put("answerData", answerData);
        argMap.put("isCorrect", isCorrect ? 1 : 0);
        namedParameterJdbcTemplate.update(QuestionQueries.INSERT_ANSWER_OPTION.getQuery(), argMap);
    }

    public void submitAnswer(long quizAssignmentId, Answer answer){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("questionId", answer.getQuestionId());
        argMap.put("answerData", answer.getAnswerData());
        argMap.put("quizAssignmentId", quizAssignmentId);
        namedParameterJdbcTemplate.update(QuestionQueries.INSERT_ANSWER.getQuery(), argMap);
    }

    public Question findQuestionById(long id) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", id);

        return namedParameterJdbcTemplate.queryForObject(QuestionQueries.SELECT_QUESTION_BY_ID.getQuery(), argMap, questionMapper);
    }
}
