package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.Course;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class CourseMapper implements RowMapper<Course> {

    @Autowired
    UserRepository userRepository;

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));
        course.setStartDate(rs.getDate("start_date"));
        course.setEndDate(rs.getDate("end_date"));

        User instructor = userRepository.findUserById(rs.getLong("instructor_id"));
        course.setInstructor(instructor);

        return course;
    }
}
