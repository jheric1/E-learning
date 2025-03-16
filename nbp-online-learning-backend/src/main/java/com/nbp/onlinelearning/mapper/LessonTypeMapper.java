package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.LessonType;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class LessonTypeMapper implements RowMapper<LessonType> {
    @Override
    public LessonType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return LessonType.valueOf(rs.getString("name"));
    }
}
