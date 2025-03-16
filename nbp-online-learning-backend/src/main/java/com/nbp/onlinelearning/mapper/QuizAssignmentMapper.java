package com.nbp.onlinelearning.mapper;

import com.nbp.onlinelearning.constants.QuizAssignmentStatus;
import com.nbp.onlinelearning.model.QuizAssignment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizAssignmentMapper implements RowMapper<QuizAssignment> {

    @Override
    public QuizAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizAssignment quizAssignment = new QuizAssignment();
        quizAssignment.setId(rs.getLong("id"));
        quizAssignment.setStudentId(rs.getLong("student_id"));
        quizAssignment.setStudentId(rs.getLong("quiz_id"));
        quizAssignment.setTotalPoints(rs.getDouble("total_points"));
        quizAssignment.setQuizAssignmentStatus(QuizAssignmentStatus.valueOf(rs.getString("name")));

        return quizAssignment;
    }

}
