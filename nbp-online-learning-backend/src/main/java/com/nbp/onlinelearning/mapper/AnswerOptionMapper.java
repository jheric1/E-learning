package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.AnswerOption;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnswerOptionMapper implements RowMapper<AnswerOption> {

    @Override
    public AnswerOption mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnswerOption answerOption = new AnswerOption();
        answerOption.setId(rs.getLong("id"));
        answerOption.setAnswerData(rs.getString("answer_data"));
        answerOption.setCorrect(rs.getInt("is_correct") == 1);
        answerOption.setQuestionId(rs.getLong("question_id"));

        return answerOption;
    }

}
