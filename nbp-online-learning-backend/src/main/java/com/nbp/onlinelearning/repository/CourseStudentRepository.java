package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.queries.CourseStudentQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseStudentRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void addStudentToCourse(long courseId, long studentId, Date applicationDate) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("studentId", studentId);
        argMap.put("courseId", courseId);
        argMap.put("applicationDate", applicationDate);
        namedParameterJdbcTemplate.update(CourseStudentQueries.INSERT_STUDENT_TO_COURSE.getQuery(), argMap);
    }

    public void removeStudentFromCourse(long studentId, long courseId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("student_id", studentId);
        argMap.put("course_id", courseId);
        namedParameterJdbcTemplate.update(CourseStudentQueries.REMOVE_STUDENT.getQuery(), argMap);

    }
}
