package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.QuestionTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.nbp.onlinelearning.queries.QuestionTypeQueries;

import java.util.HashMap;
import java.util.Map;

@Repository
public class QuestionTypeRepository {
    @Autowired
    private QuestionTypeMapper questionTypeMapper;
     @Autowired
     NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Object findQuestionTypeByName(String name) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("name", name);
        return namedParameterJdbcTemplate.queryForObject(QuestionTypeQueries.SELECT_QUESTION_TYPE_BY_NAME.getQuery(), argMap, (rs, rowNum) -> rs.getLong("id"));
    }
}
