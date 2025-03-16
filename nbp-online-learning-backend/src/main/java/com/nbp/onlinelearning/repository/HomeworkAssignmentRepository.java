package com.nbp.onlinelearning.repository;

import com.nbp.onlinelearning.constants.HomeworkAssignmentStatus;
import com.nbp.onlinelearning.mapper.HomeworkAssignmentDtoMapper;
import com.nbp.onlinelearning.mapper.HomeworkAssignmentMapper;
import com.nbp.onlinelearning.model.GradeHomeworkDto;
import com.nbp.onlinelearning.model.HomeworkAssignment;
import com.nbp.onlinelearning.model.HomeworkAssignmentDto;
import com.nbp.onlinelearning.queries.HomeworkAssignmentQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HomeworkAssignmentRepository {

    @Autowired
    HomeworkAssignmentMapper homeworkAssignmentMapper;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    HomeworkAssignmentDtoMapper homeworkAssignmentDtoMapper;

    public List<HomeworkAssignment> findAllHomeworkAssignments() {
        return namedParameterJdbcTemplate.query(HomeworkAssignmentQueries.SELECT_ALL_HOMEWORK_ASSIGNMENTS.getQuery(), homeworkAssignmentMapper);
    }

    public HomeworkAssignment findHomeworkAssignmentByHomeworkId(long homeworkId, long studentId) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("homeworkId", homeworkId);
        argMap.put("studentId", studentId);
        try {
            return namedParameterJdbcTemplate.queryForObject(HomeworkAssignmentQueries.FIND_HOMEWORK_ASSIGNMENT_BY_HOMEWORK_ID.getQuery(), argMap, homeworkAssignmentMapper);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<HomeworkAssignmentDto> findHomeworkAssignmentsByHomeworkId(long homeworkId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("homeworkId", homeworkId);
        return namedParameterJdbcTemplate.query(HomeworkAssignmentQueries.FIND_HOMEWORK_ASSIGNMENTS_BY_HOMEWORK_ID.getQuery(), argMap, homeworkAssignmentDtoMapper);
    }

    public HomeworkAssignment submitHomework(HomeworkAssignment homeworkAssignment){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("id", homeworkAssignment.getId());
        argMap.put("submitDate", homeworkAssignment.getSubmitDate());
        argMap.put("homeworkDataUrl", homeworkAssignment.getHomeworkDataUrl());
        argMap.put("statusId", findStatusId(homeworkAssignment.getHomeworkAssignmentStatus().getStatus()));

        namedParameterJdbcTemplate.update(HomeworkAssignmentQueries.SUBMIT_HOMEWORK.getQuery(), argMap);
        return homeworkAssignment;
    }

    public void assignHomework(long homeworkId, long studentId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("homeworkId", homeworkId);
        argMap.put("studentId", studentId);
        argMap.put("statusId", findStatusId(HomeworkAssignmentStatus.NOT_SUBMITTED.getStatus()));
        namedParameterJdbcTemplate.update(HomeworkAssignmentQueries.ASSIGN_HOMEWORK_TO_STUDENT.getQuery(), argMap);
    }

    public void removeHomework(long homeworkId, long studentId){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("homeworkId", homeworkId);
        argMap.put("studentId", studentId);
        namedParameterJdbcTemplate.update(HomeworkAssignmentQueries.REMOVE_HOMEWORK_FROM_STUDENT.getQuery(), argMap);
    }

    private Long findStatusId(String status){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("name", status);
        return namedParameterJdbcTemplate.queryForObject(HomeworkAssignmentQueries.SELECT_HOMEWORK_STATUS_ID_BY_NAME.getQuery(), argMap, (rs, rowNum) -> rs.getLong("id"));
    }

    public void gradeHomework(GradeHomeworkDto gradeHomeworkDto){
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("homeworkAssignmentId", gradeHomeworkDto.getHomeworkAssignmentId());
        argMap.put("points", gradeHomeworkDto.getPoints());

        namedParameterJdbcTemplate.update(HomeworkAssignmentQueries.GRADE_HOMEWORK.getQuery(), argMap);
    }
}
