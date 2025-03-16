package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.constants.HomeworkAssignmentStatus;
import com.nbp.onlinelearning.model.GradeHomeworkDto;
import com.nbp.onlinelearning.model.HomeworkAssignment;
import com.nbp.onlinelearning.model.HomeworkAssignmentDto;
import com.nbp.onlinelearning.repository.HomeworkAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HomeworkAssignmentService {

    @Autowired
    HomeworkAssignmentRepository homeworkAssignmentRepository;

    public List<HomeworkAssignment> getAllHomeworkAssignments() {
        return homeworkAssignmentRepository.findAllHomeworkAssignments();
    }

    public HomeworkAssignment findHomeworkAssignmentByHomeworkId(long homeworkId, long studentId){
        return homeworkAssignmentRepository.findHomeworkAssignmentByHomeworkId(homeworkId, studentId);
    }

    public List<HomeworkAssignmentDto> findHomeworkAssignmentsByHomeworkId(long homeworkId){
        return homeworkAssignmentRepository.findHomeworkAssignmentsByHomeworkId(homeworkId);
    }

    public HomeworkAssignment submitHomework(HomeworkAssignment homeworkAssignment){
        homeworkAssignment.setSubmitDate(new Date());
        homeworkAssignment.setHomeworkAssignmentStatus(HomeworkAssignmentStatus.COMPLETED);
        return homeworkAssignmentRepository.submitHomework(homeworkAssignment);
    }

    public void gradeHomework(GradeHomeworkDto gradeHomeworkDto){
        homeworkAssignmentRepository.gradeHomework(gradeHomeworkDto);
    }

}
