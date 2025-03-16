package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.LessonType;
import com.nbp.onlinelearning.model.Lesson;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

@Component
public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getLong("id"));
        lesson.setActive(rs.getBoolean("is_active"));
        lesson.setTittle(rs.getString("title"));
        lesson.setUrl(rs.getString("url"));
        lesson.setLessonData(rs.getString("lesson_data"));
        lesson.setStartDate(rs.getDate("start_date"));
        lesson.setEndDate(rs.getDate("end_date"));
        String type = rs.getString("name");
        if (LessonType.containsLessonType(type)) {
            lesson.setLessonType(LessonType.valueOf(type));
        }
        lesson.setCourseId(rs.getLong("course_id"));
        return lesson;

    }

}
