package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.constants.HomeworkAssignmentStatus;
import com.nbp.onlinelearning.constants.QuizAssignmentStatus;
import com.nbp.onlinelearning.mapper.QuizAssignmentMapper;
import com.nbp.onlinelearning.mapper.QuizMapper;
import com.nbp.onlinelearning.mapper.QuizResultMapper;
import com.nbp.onlinelearning.model.Quiz;
import com.nbp.onlinelearning.model.QuizAssignment;
import com.nbp.onlinelearning.model.QuizResultDto;
import com.nbp.onlinelearning.queries.HomeworkAssignmentQueries;
import com.nbp.onlinelearning.queries.QuizQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuizRepository {
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    QuizAssignmentMapper quizAssignmentMapper;

    @Autowired
    QuizResultMapper quizResultMapper;

    public List<Quiz> findAllQuizzes() {
        return namedParameterJdbcTemplate.query(QuizQueries.SELECT_ALL_QUIZZES.getQuery(), quizMapper);
    }

    public Quiz findQuizById(long quizId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", quizId);
        return namedParameterJdbcTemplate.queryForObject(QuizQueries.SELECT_QUIZ_BY_ID.getQuery(), argMap, quizMapper);
    }

    public Integer createQuiz(Quiz quiz) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("course_id", quiz.getCourseId());
        mapSqlParameterSource.addValue("start_date", quiz.getStartDate());
        mapSqlParameterSource.addValue("deadline_date", quiz.getDeadlineDate());
        mapSqlParameterSource.addValue("title", quiz.getTitle());
        mapSqlParameterSource.addValue("description", quiz.getDescription());
        mapSqlParameterSource.addValue("is_active", quiz.isActive() ? 1 : 0);
        mapSqlParameterSource.addValue("max_points", quiz.getMaxPoints());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QuizQueries.CREATE_QUIZ.getQuery(), mapSqlParameterSource, keyHolder, new String[]{"id"});
        return keyHolder.getKeyAs(BigDecimal.class).intValueExact();
    }

    public boolean deleteQuiz(long id) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", id);
        namedParameterJdbcTemplate.update(QuizQueries.DELETE_QUIZ.getQuery(), argMap);
        return true;
    }

    public List<Quiz> findQuizzesByCourseId(long courseId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("course_id", courseId);
        return namedParameterJdbcTemplate.query(QuizQueries.SELECT_QUIZZES_BY_COURSE_ID.getQuery(), argMap, quizMapper);
    }

    public void assignQuiz(long quizId, long studentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizId", quizId);
        argMap.put("studentId", studentId);
        argMap.put("statusId", findStatusId(QuizAssignmentStatus.NOT_STARTED.getStatus()));
        namedParameterJdbcTemplate.update(QuizQueries.ASSIGN_QUIZ_TO_STUDENT.getQuery(), argMap);
    }

    public void removeQuiz(long quizId, long studentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizId", quizId);
        argMap.put("studentId", studentId);
        namedParameterJdbcTemplate.update(QuizQueries.REMOVE_QUIZ_FROM_STUDENT.getQuery(), argMap);
    }

    private Long findStatusId(String status) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("name", status);
        return namedParameterJdbcTemplate.queryForObject(QuizQueries.SELECT_QUIZ_STATUS_ID_BY_NAME.getQuery(), argMap, (rs, rowNum) -> rs.getLong("id"));
    }

    public QuizAssignment getQuizAssignment(long studentId, long quizId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("studentId", studentId);
        argMap.put("quizId", quizId);
        return namedParameterJdbcTemplate.queryForObject(QuizQueries.SELECT_QUIZ_ASSIGNMENT.getQuery(), argMap, quizAssignmentMapper);
    }

    public void submitQuizAssignment(long quizAssignmentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizAssignmentId", quizAssignmentId);
        argMap.put("statusId", findStatusId(QuizAssignmentStatus.COMPLETED.getStatus()));
        namedParameterJdbcTemplate.update(QuizQueries.SUBMIT_QUIZ.getQuery(), argMap);
    }

    public Long findQuizByAssignmentId(long quizAssignmentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizAssignmentId", quizAssignmentId);
        return namedParameterJdbcTemplate.queryForObject(QuizQueries.SELECT_QUIZ_ID_BY_ASSIGNMENT.getQuery(), argMap, (rs, rowNum) -> rs.getLong("quiz_id"));
    }

    public void gradeQuiz(long quizAssignmentId, double totalPoints) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizAssignmentId", quizAssignmentId);
        argMap.put("totalPoints", totalPoints);
        namedParameterJdbcTemplate.update(QuizQueries.UPDATE_QUIZ_POINTS.getQuery(), argMap);
    }

    public List<QuizResultDto> getQuizResults(long quizId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("quizId", quizId);
        argMap.put("statusId", findStatusId(QuizAssignmentStatus.COMPLETED.getStatus()));
        return namedParameterJdbcTemplate.query(QuizQueries.SELECT_QUIZ_RESULTS.getQuery(), argMap, quizResultMapper);
    }
}
