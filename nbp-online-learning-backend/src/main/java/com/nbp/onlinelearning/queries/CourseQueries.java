package com.nbp.onlinelearning.queries;

public enum CourseQueries {
    SELECT_ALL_COURSES("SELECT * FROM nbp24t2.nbp_course"),
    SELECT_COURSE_BY_ID("SELECT * FROM nbp24t2.nbp_course WHERE id = :id"),
    SELECT_COURSES_BY_INSTRUCTOR_ID("SELECT * FROM nbp24t2.nbp_course WHERE instructor_id = :instructor_id"),
    SELECT_COURSES_BY_STUDENT_ID("SELECT c.* FROM nbp24t2.nbp_course c, nbp24t2.nbp_course_student cs WHERE cs.course_id = c.id AND cs.student_id = :student_id"),
    SELECT_FREE_COURSES_BY_STUDENT_ID("SELECT UNIQUE c.* FROM nbp24t2.nbp_course c LEFT JOIN nbp24t2.nbp_course_student cs ON cs.course_id = c.id WHERE c.ID NOT IN (SELECT UNIQUE course_id FROM NBP24T2.NBP_COURSE_STUDENT WHERE student_id = :studentId)"),
    SELECT_COURSES_WITH_INSTRUCTOR("SELECT c.*, u.* FROM nbp24t2.nbp_course c JOIN nbp.nbp_user u ON c.instructor_id = u.id"),
    CREATE_COURSE("INSERT INTO nbp24t2.nbp_course (title, start_date, end_date, description, instructor_id) VALUES (:title, :start_date, :end_date, :description, :instructor_id)"),
    SET_PROFESSOR("UPDATE nbp24t2.nbp_course SET instructor_id = :professor_id WHERE id = :id"),
    DELETE_COURSE("DELETE FROM nbp24T2.nbp_course WHERE id = :courseId");

    private final String query;

    CourseQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
