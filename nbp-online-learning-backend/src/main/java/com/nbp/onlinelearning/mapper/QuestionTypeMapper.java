package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.QuestionType;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class QuestionTypeMapper implements RowMapper<QuestionType> {
    @Override
    public QuestionType mapRow(ResultSet rs, int rowNum) throws SQLException{
        return QuestionType.valueOf(rs.getString("name"));
    }
}

