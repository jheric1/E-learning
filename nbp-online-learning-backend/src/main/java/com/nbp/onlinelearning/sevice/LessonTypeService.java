package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.repository.LessonTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonTypeService {
    @Autowired
    LessonTypeRepository lessonTypeRepository;

    public Long getLessonTypeByName(String lessonType) {
        return lessonTypeRepository.findLessonTypeIdByName(lessonType);
    }
}
