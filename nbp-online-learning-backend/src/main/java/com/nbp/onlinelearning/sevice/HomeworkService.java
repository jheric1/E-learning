package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.model.Homework;
import com.nbp.onlinelearning.model.User;
import com.nbp.onlinelearning.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HomeworkAssignmentRepository homeworkAssignmentRepository;

    public List<Homework> getAllHomeworks() {
        return homeworkRepository.findAllHomeworks();
    }
    public Homework getHomeworkById(long id){
        return homeworkRepository.findHomeworkById(id);
    }

    public Homework createHomework(Homework homework) {
        int homeworkId = homeworkRepository.createHomework(homework);
        List<User> students = userRepository.findAllStudentsFromCourse(homework.getCourseId());
        for(User student: students){
            homeworkAssignmentRepository.assignHomework(homeworkId, student.getId());
        }
        return homework;
    }
}
