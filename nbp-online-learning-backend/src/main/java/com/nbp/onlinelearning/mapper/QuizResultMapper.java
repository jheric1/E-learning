package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Quiz;
import com.nbp.onlinelearning.model.QuizResultDto;
import com.nbp.onlinelearning.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizResultMapper implements RowMapper<QuizResultDto> {
    @Autowired
    CourseRepository courseRepository;
    @Override
    public QuizResultDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizResultDto quizResult = new QuizResultDto();
        quizResult.setFirstName(rs.getString("first_name"));
        quizResult.setLastName(rs.getString("last_name"));
        quizResult.setEmail(rs.getString("email"));
        quizResult.setTotalPoints(rs.getDouble("total_points"));
        return quizResult;
    }
}
