package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.CourseMapper;
import com.nbp.onlinelearning.model.Course;
import com.nbp.onlinelearning.model.CreateCourseDTO;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.queries.CourseQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseRepository {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    UserRepository userRepository;

    public List<Course> findAllCourses() {
        return namedParameterJdbcTemplate.query(CourseQueries.SELECT_ALL_COURSES.getQuery(), courseMapper);
    }


    public Course findCourseById(long courseId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", courseId);
        return namedParameterJdbcTemplate.queryForObject(CourseQueries.SELECT_COURSE_BY_ID.getQuery(), argMap, courseMapper);

    }

    public List<Course> findCoursesByInstructorId(long instructorId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("instructor_id", instructorId);
        return namedParameterJdbcTemplate.query(CourseQueries.SELECT_COURSES_BY_INSTRUCTOR_ID.getQuery(), argMap,courseMapper);


    }

    public List<Course> findCoursesByStudentId(long studentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("student_id", studentId);
        return namedParameterJdbcTemplate.query(CourseQueries.SELECT_COURSES_BY_STUDENT_ID.getQuery(), argMap, courseMapper);
    }

    public List<Course> findFreeCoursesByStudentId(long studentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("studentId", studentId);
        return namedParameterJdbcTemplate.query(CourseQueries.SELECT_FREE_COURSES_BY_STUDENT_ID.getQuery(), argMap, courseMapper);
    }

    public List<Course> findAllCoursesWithInstructors() {
        return namedParameterJdbcTemplate.query(CourseQueries.SELECT_COURSES_WITH_INSTRUCTOR.getQuery(),
               courseMapper);
    }

    public void createCourse(CreateCourseDTO course) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("title", course.getTitle());
        argMap.put("description", course.getDescription());
        argMap.put("start_date", course.getStartDate());
        argMap.put("end_date", course.getEndDate());
        argMap.put("instructor_id", course.getInstructorId());
        namedParameterJdbcTemplate.update(CourseQueries.CREATE_COURSE.getQuery(), argMap);
    }

    public void setProfessorOnCourse(long courseId, long professorId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", courseId);
        argMap.put("professor_id", professorId);
        namedParameterJdbcTemplate.update(CourseQueries.SET_PROFESSOR.getQuery(), argMap);
    }

    public void deleteCourse(long courseId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("courseId", courseId);
        namedParameterJdbcTemplate.update(CourseQueries.DELETE_COURSE.getQuery(), argMap);
    }
}
