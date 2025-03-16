package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.HomeworkMapper;
import com.nbp.onlinelearning.model.Homework;
import com.nbp.onlinelearning.queries.HomeworkQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HomeworkRepository {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    HomeworkMapper homeworkMapper;

    public List<Homework> findAllHomeworks() {
        return namedParameterJdbcTemplate.query(HomeworkQueries.SELECT_ALL_HOMEWORKS.getQuery(), new HomeworkMapper());
    }

    public Homework findHomeworkById(long homeworkId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", homeworkId);
        return namedParameterJdbcTemplate.queryForObject(HomeworkQueries.SELECT_HOMEWORK_BY_ID.getQuery(), argMap, homeworkMapper);
    }

    public Integer createHomework(Homework homework) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("course_id", homework.getCourseId());
        mapSqlParameterSource.addValue("max_points", homework.getMaxPoints());
        mapSqlParameterSource.addValue("start_date", homework.getStartDate());
        mapSqlParameterSource.addValue("deadline_date", homework.getDeadlineDate());
        mapSqlParameterSource.addValue("title", homework.getTitle());
        mapSqlParameterSource.addValue("description", homework.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(HomeworkQueries.CREATE_HOMEWORK.getQuery(), mapSqlParameterSource, keyHolder, new String[]{"id"});
        return keyHolder.getKeyAs(BigDecimal.class).intValueExact();
    }

    public List<Homework> findHomeworksByCourseId(long courseId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("course_id", courseId);
        return namedParameterJdbcTemplate.query(HomeworkQueries.SELECT_HOMEWORKS_BY_COURSE_ID.getQuery(), argMap, homeworkMapper);
    }
}
