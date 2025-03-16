package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.HomeworkAssignmentStatus;
import com.nbp.onlinelearning.model.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HomeworkAssignmentMapper implements RowMapper<HomeworkAssignment> {
    @Override
    public HomeworkAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
        HomeworkAssignment homeworkAssignment = new HomeworkAssignment();
        homeworkAssignment.setId(rs.getLong("id"));
        homeworkAssignment.setHomeworkId(rs.getLong("homework_id"));
        homeworkAssignment.setStudentId(rs.getLong("student_id"));
        homeworkAssignment.setPoints(rs.getInt("points"));
        homeworkAssignment.setSubmitDate(rs.getDate("submit_date"));
        homeworkAssignment.setHomeworkDataUrl(rs.getString("homework_data_url"));
        homeworkAssignment.setHomeworkAssignmentStatus(HomeworkAssignmentStatus.valueOf(rs.getString("name")));

        return homeworkAssignment;
    }
}
