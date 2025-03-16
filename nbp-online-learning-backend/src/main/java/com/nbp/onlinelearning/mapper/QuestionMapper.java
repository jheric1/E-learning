package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.QuestionType;
import com.nbp.onlinelearning.model.Question;
import com.nbp.onlinelearning.model.Quiz;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setId(rs.getLong("id"));
        question.setText(rs.getString("text"));
        question.setMaxPoints(rs.getDouble("max_points"));
        question.setQuizId(rs.getLong("quiz_id"));

        return question;

    }

}
