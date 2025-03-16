package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.RoleMapper;
import com.nbp.onlinelearning.queries.RoleQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleRepository {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Long findRoleIdByName(String roleName){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("name", roleName);
        return namedParameterJdbcTemplate.queryForObject(RoleQueries.SELECT_ROLE_ID_BY_NAME.getQuery(), argMap, (rs, rowNum) -> rs.getLong("id"));
    }
}
