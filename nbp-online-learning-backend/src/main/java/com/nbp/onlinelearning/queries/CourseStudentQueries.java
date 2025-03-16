package com.nbp.onlinelearning.queries;

public enum CourseStudentQueries {
    INSERT_STUDENT_TO_COURSE("INSERT INTO nbp24t2.nbp_course_student (course_id, student_id, application_date) VALUES (:courseId, :studentId, :applicationDate)"),
    REMOVE_STUDENT("DELETE FROM nbp24t2.nbp_course_student WHERE course_id = :course_id and student_id = :student_id");

    private final String query;

    CourseStudentQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
