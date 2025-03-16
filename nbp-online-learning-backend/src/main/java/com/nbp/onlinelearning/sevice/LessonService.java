package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.model.Lesson;
import com.nbp.onlinelearning.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    LessonRepository lessonRepository;

    public List<Lesson> findAllLessons() {
        return lessonRepository.findAllLessons();
    }

    public List<Lesson> findLessonsByCourseId(long id) {
        return lessonRepository.findLessonsByCourseId(id);
    }

    public Lesson findLessonsById(long id) {
        return lessonRepository.findLessonsById(id);
    }

    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.createLesson(lesson);
    }

    public boolean deleteLesson(long id) {
        return lessonRepository.deleteLesson(id);
    }
}
