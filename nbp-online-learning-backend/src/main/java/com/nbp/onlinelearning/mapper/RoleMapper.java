package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.UserRole;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleMapper implements RowMapper<UserRole> {
    @Override
    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserRole.valueOf(rs.getString("name"));
    }
}
