package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.mapper.UserActivityDtoMapper;
import com.nbp.onlinelearning.mapper.UserMapper;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.model.UserActivityDto;
import com.nbp.onlinelearning.queries.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserActivityDtoMapper userActivityDtoMapper;

    public List<User> findAllUsers() {
        return namedParameterJdbcTemplate.query(UserQueries.SELECT_ALL_USERS.getQuery(), userMapper);
    }

    public User findUserByUsername(String username) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("username", username);
        return namedParameterJdbcTemplate.queryForObject(UserQueries.SELECT_USER_BY_USERNAME.getQuery(), argMap, userMapper);
    }

    public User findUserByEmail(String email) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("email", email);
        return namedParameterJdbcTemplate.queryForObject(UserQueries.SELECT_USER_BY_EMAIL.getQuery(), argMap, userMapper);
    }

    public User findUserById(long id) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(UserQueries.SELECT_USER_BY_ID.getQuery(), argMap, userMapper);
    }

    public User createUser(User user) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("first_name", user.getFirstName());
        argMap.put("last_name", user.getLastName());
        argMap.put("email", user.getEmail());
        argMap.put("password", user.getPassword());
        argMap.put("username", user.getUsername());
        argMap.put("phone_number", user.getPhoneNumber());
        argMap.put("birth_date", user.getBirthDate());
        argMap.put("role_id", roleRepository.findRoleIdByName(user.getRole().getRole()));

        namedParameterJdbcTemplate.update(UserQueries.INSERT_USER.getQuery(), argMap);
        return user;
    }

    public User updateUser(User user) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", user.getId());
        argMap.put("first_name", user.getFirstName());
        argMap.put("last_name", user.getLastName());
        argMap.put("email", user.getEmail());
        argMap.put("password", user.getPassword());
        argMap.put("username", user.getUsername());
        argMap.put("phone_number", user.getPhoneNumber());
        argMap.put("birth_date", user.getBirthDate());

        namedParameterJdbcTemplate.update(UserQueries.UPDATE_USER_WITHOUT_ROLE.getQuery(), argMap);
        return user;
    }

    public boolean deleteUser(long id) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", id);

        namedParameterJdbcTemplate.update(UserQueries.DELETE_USER.getQuery(), argMap);
        return true;
    }

    public List<User> findAllStudents() {
        return namedParameterJdbcTemplate.query(UserQueries.SELECT_ALL_STUDENTS.getQuery(), userMapper);
    }

    public List<User> findAllInstructors() {
        return namedParameterJdbcTemplate.query(UserQueries.SELECT_ALL_INSTRUCTORS.getQuery(), userMapper);
    }

    public List<User> findAllStudentsFromCourse(long courseId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("courseId", courseId);
        return namedParameterJdbcTemplate.query(UserQueries.SELECT_STUDENTS_FROM_COURSE.getQuery(), argMap, userMapper);
    }

    public List<UserActivityDto> getLatestUserActivity(){
        return namedParameterJdbcTemplate.query(UserQueries.SELECT_LATEST_ACTIVITY.getQuery(), userActivityDtoMapper);
    }
}
