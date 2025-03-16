package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Course;
import com.nbp.onlinelearning.model.CourseStudent;
import com.nbp.onlinelearning.model.Grade;
import com.nbp.onlinelearning.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
public class CourseStudentMapper implements RowMapper<CourseStudent> {

    @Override
    public CourseStudent mapRow(ResultSet rs, int rowNum) throws SQLException {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setApplicationDate(rs.getDate("application_date"));

        // Mapping User object
        User student = new User();
        student.setId(rs.getLong("student_id"));
        courseStudent.setStudent(student);

        // Mapping Course object
        Course course = new Course();
        course.setId(rs.getInt("course_id"));
        courseStudent.setCourse(course);

        // Mapping Grade object
        Grade grade = new Grade();
        grade.setId(rs.getInt("grade_id"));
        courseStudent.setGrade(grade);

        return courseStudent;
    }
}
