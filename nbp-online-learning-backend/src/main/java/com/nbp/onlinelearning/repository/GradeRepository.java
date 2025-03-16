package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.GradeMapper;
import com.nbp.onlinelearning.model.Grade;
import com.nbp.onlinelearning.queries.GradeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GradeRepository {

    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Grade> getAllGrades(){
        return namedParameterJdbcTemplate.query(GradeQueries.SELECT_ALL_GRADES.getQuery(), gradeMapper);
    }

    public Grade findGradeByDescription(String description){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("description", description);
        return namedParameterJdbcTemplate.queryForObject(GradeQueries.SELECT_GRADE_BY_DESCRIPTION.getQuery(), argMap, gradeMapper);
    }
    public Grade findGradeByValue(long value){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("value", value);
        return namedParameterJdbcTemplate.queryForObject(GradeQueries.SELECT_GRADE_BY_VALUE.getQuery(), argMap, gradeMapper);
    }

}
