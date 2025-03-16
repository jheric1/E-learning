package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Quiz;
import com.nbp.onlinelearning.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizMapper implements RowMapper<Quiz> {
    @Autowired
    CourseRepository courseRepository;
    @Override
    public Quiz mapRow(ResultSet rs, int rowNum) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setId(rs.getLong("id"));
        quiz.setStartDate(rs.getDate("start_date"));
        quiz.setDeadlineDate(rs.getDate("deadline_date"));
        quiz.setTitle(rs.getString("title"));
        quiz.setDescription(rs.getString("description"));
        quiz.setActive(rs.getBoolean("is_active"));
        quiz.setMaxPoints(rs.getInt("max_points"));
        quiz.setCourseId(rs.getLong("course_id"));
        return quiz;
    }
}
