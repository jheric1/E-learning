package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.constants.HomeworkAssignmentStatus;
import com.nbp.onlinelearning.model.*;
import com.nbp.onlinelearning.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    HomeworkAssignmentRepository homeworkAssignmentRepository;


    public List<Course> getAllCourses() {
        return courseRepository.findAllCourses();
    }

    public Course getCourseById(long id) {
        Course course = courseRepository.findCourseById(id);
        List<Quiz> quizList = quizRepository.findQuizzesByCourseId(id);
        course.setQuizList(quizList);
        List<Lesson> lessonList = lessonRepository.findLessonsByCourseId(id);
        course.setLessonList(lessonList);
        List<Homework> homeworkList = homeworkRepository.findHomeworksByCourseId(id);
        course.setHomeworkList(homeworkList);

        return course;
    }

    public List<Course> getCourseByInstructor(long instructorId) {
        List<Course> courses = courseRepository.findCoursesByInstructorId(instructorId);
        for (Course course : courses) {
            course.setQuizList(quizRepository.findQuizzesByCourseId(course.getId()));
            course.setLessonList(lessonRepository.findLessonsByCourseId(course.getId()));
            course.setHomeworkList(homeworkRepository.findHomeworksByCourseId(course.getId()));
        }
        return courses;
    }

    public List<Course> findStudentCourses(long studentId) {
        List<Course> courses = courseRepository.findCoursesByStudentId(studentId);
        for (Course course : courses) {
            course.setQuizList(quizRepository.findQuizzesByCourseId(course.getId()));
            course.setLessonList(lessonRepository.findLessonsByCourseId(course.getId()));
            course.setHomeworkList(homeworkRepository.findHomeworksByCourseId(course.getId()));
        }
        return courses;
    }

    public List<Course> findStudentFreeCourses(long studentId) {
        List<Course> courses = courseRepository.findFreeCoursesByStudentId(studentId);
        for (Course course : courses) {
            course.setQuizList(quizRepository.findQuizzesByCourseId(course.getId()));
            course.setLessonList(lessonRepository.findLessonsByCourseId(course.getId()));
            course.setHomeworkList(homeworkRepository.findHomeworksByCourseId(course.getId()));
        }
        return courses;
    }

    public void createCourse(CreateCourseDTO c) {
        courseRepository.createCourse(c);
    }

    public void addStudentOnCourse(long studentId, long courseId) {
        List<Course> studentCourses = findStudentCourses(studentId);
        if (studentCourses.stream().anyMatch(course -> course.getId() == courseId)) {
            throw new IllegalArgumentException("Student is already on this course");
        }

        courseStudentRepository.addStudentToCourse(courseId, studentId, new Date());

        List<Homework> homeworkList = homeworkRepository.findHomeworksByCourseId(courseId);
        for (Homework homework : homeworkList) {
            homeworkAssignmentRepository.assignHomework(homework.getId(), studentId);
        }

        List<Quiz> quizList = quizRepository.findQuizzesByCourseId(courseId);
        for (Quiz quiz : quizList) {
            quizRepository.assignQuiz(quiz.getId(), studentId);
        }
    }

    public void removeStudentFromCourse(long studentId, long courseId) {
        List<Course> studentCourses = findStudentCourses(studentId);
        if (studentCourses.stream().allMatch(course -> course.getId() != courseId)) {
            throw new IllegalArgumentException("Student hasn't applied to this course");
        }

        courseStudentRepository.removeStudentFromCourse(studentId, courseId);

        List<Homework> homeworkList = homeworkRepository.findHomeworksByCourseId(courseId);
        for (Homework homework : homeworkList) {
            homeworkAssignmentRepository.removeHomework(homework.getId(), studentId);
        }

        List<Quiz> quizList = quizRepository.findQuizzesByCourseId(courseId);
        for (Quiz quiz : quizList) {
            quizRepository.removeQuiz(quiz.getId(), studentId);
        }
    }

    public void addProfessorOnCourse(long courseId, long professorId) {
        User user = userRepository.findUserById(professorId);
        if (user != null && Objects.equals(user.getRole().getRole(), "INSTRUCTOR")) {
            courseRepository.setProfessorOnCourse(courseId, professorId);
        }
    }

    public void deleteCourse(long courseId){
        courseRepository.deleteCourse(courseId);
    }
}
