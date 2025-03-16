package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.LessonTypeMapper;
import com.nbp.onlinelearning.queries.LessonTypeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class LessonTypeRepository {

    @Autowired
    private LessonTypeMapper lessonTypeMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Long findLessonTypeIdByName(String lessonType){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("name", lessonType);
        return namedParameterJdbcTemplate.queryForObject(LessonTypeQueries.SELECT_LESSON_TYPE_BY_NAME.getQuery(), argMap, (rs, rowNum) -> rs.getLong("id"));
    }
}
