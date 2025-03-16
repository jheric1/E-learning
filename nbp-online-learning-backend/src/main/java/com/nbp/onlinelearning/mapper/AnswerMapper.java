package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Answer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnswerMapper implements RowMapper<Answer> {

    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Answer answer = new Answer();
        answer.setId(rs.getLong("id"));
        answer.setAnswerData(rs.getString("answer_data"));
        answer.setQuestionId(rs.getLong("question_id"));
        return answer;
    }
}
