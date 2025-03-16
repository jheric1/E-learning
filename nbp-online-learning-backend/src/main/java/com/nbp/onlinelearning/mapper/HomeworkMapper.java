package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Homework;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomeworkMapper implements RowMapper<Homework> {
    @Override
    public Homework mapRow(ResultSet rs, int rowNum) throws SQLException {
        Homework homework = new Homework();
        homework.setId(rs.getLong("id"));
        homework.setCourseId(rs.getLong("course_id"));
        homework.setMaxPoints(rs.getInt("max_points"));
        homework.setStartDate(rs.getDate("start_date"));
        homework.setDeadlineDate(rs.getDate("deadline_date"));
        homework.setTitle(rs.getString("title"));
        homework.setDescription(rs.getString("description"));

        return homework;
    }
}
