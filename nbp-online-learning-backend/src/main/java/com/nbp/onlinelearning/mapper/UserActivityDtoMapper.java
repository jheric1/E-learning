package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.UserActivityDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserActivityDtoMapper implements RowMapper<UserActivityDto> {

    @Override
    public UserActivityDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setActionName(rs.getString("action_name"));
        userActivityDto.setTableName(rs.getString("table_name"));
        userActivityDto.setDateTime(rs.getDate("date_time"));
        userActivityDto.setDbUser(rs.getString("db_user"));
        return userActivityDto;
    }
}
