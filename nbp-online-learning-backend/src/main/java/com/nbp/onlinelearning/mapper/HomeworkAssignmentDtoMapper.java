package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.model.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomeworkAssignmentDtoMapper implements RowMapper<HomeworkAssignmentDto> {
    @Override
    public HomeworkAssignmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        HomeworkAssignmentDto homeworkAssignment = new HomeworkAssignmentDto();
        homeworkAssignment.setFirstName(rs.getString("first_name"));
        homeworkAssignment.setLastName(rs.getString("last_name"));
        homeworkAssignment.setEmail(rs.getString("email"));
        homeworkAssignment.setPoints(rs.getDouble("points"));
        homeworkAssignment.setSubmitDate(rs.getDate("submit_date"));
        homeworkAssignment.setHomeworkAssignmentId(rs.getLong("id"));
        homeworkAssignment.setHomeworkDataUrl(rs.getString("homework_data_url"));

        return homeworkAssignment;
    }
}
