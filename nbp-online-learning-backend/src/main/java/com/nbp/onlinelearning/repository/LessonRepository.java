package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.LessonMapper;
import com.nbp.onlinelearning.model.Lesson;
import com.nbp.onlinelearning.queries.LessonQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class LessonRepository {
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LessonTypeRepository lessonTypeRepository;

    public List<Lesson> findAllLessons() {
        return namedParameterJdbcTemplate.query(LessonQueries.SELECT_ALL_LESSONS.getQuery(),lessonMapper);
    }

    public List<Lesson> findLessonsByCourseId(long courseId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("course_id", courseId);
        return namedParameterJdbcTemplate.query(LessonQueries.SELECT_LESSONS_BY_COURSE_ID.getQuery(), argMap, lessonMapper);
    }

    public Lesson findLessonsById(long id) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(LessonQueries.SELECT_LESSONS_BY_ID.getQuery(), argMap, lessonMapper);
    }

    public Lesson createLesson(Lesson lesson) {
        Map<String, Object>argMap = new HashMap<>();
        argMap.put("url", lesson.getUrl());
        argMap.put("lesson_data", lesson.getLessonData());
        argMap.put("start_date", lesson.getStartDate());
        argMap.put("end_date", lesson.getEndDate());
        argMap.put("is_active", lesson.isActive());
        argMap.put("title", lesson.getTittle());
        argMap.put("course_id", lesson.getCourseId());
        argMap.put("lesson_type_id", lessonTypeRepository.findLessonTypeIdByName(lesson.getLessonType().getLessonType()));
        namedParameterJdbcTemplate.update(LessonQueries.INSERT_LESSON.getQuery(), argMap);
        return  lesson;
    }

    public boolean deleteLesson(long id) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", id);

        namedParameterJdbcTemplate.update(LessonQueries.DELETE_LESSON.getQuery(), argMap);
        return true;
    }
}
