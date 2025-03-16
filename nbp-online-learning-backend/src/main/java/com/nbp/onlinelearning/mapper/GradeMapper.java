package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Grade;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GradeMapper implements RowMapper<Grade> {
    @Override
    public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getLong("id"));
        grade.setDescription(rs.getString("description"));
        grade.setValue(rs.getLong("value"));
        return grade;
    }
}
