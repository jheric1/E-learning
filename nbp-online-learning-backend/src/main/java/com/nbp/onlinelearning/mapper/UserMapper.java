package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.UserRole;
import com.nbp.onlinelearning.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setBirthDate(rs.getDate("birth_date"));
        String role = rs.getString("name");
        if (UserRole.containsRole(role)) {
            user.setRole(UserRole.valueOf(role));
        } else {
            user.setRole(UserRole.OTHER);
        }
        return user;
    }
}
