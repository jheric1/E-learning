package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.model.Grade;
import com.nbp.onlinelearning.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.getAllGrades();
    }

    public Grade findGradeByDescription(String description) {
        return gradeRepository.findGradeByDescription(description);
    }

    public Grade findGradeByValue(long value) {
        return gradeRepository.findGradeByValue(value);
    }
}
