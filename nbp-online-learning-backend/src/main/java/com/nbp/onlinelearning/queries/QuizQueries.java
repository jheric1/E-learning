package com.nbp.onlinelearning.queries;

public enum QuizQueries {
    SELECT_ALL_QUIZZES("SELECT * FROM nbp24t2.nbp_quiz"),
    SELECT_QUIZ_BY_ID("SELECT * FROM nbp24t2.nbp_quiz WHERE id = :id"),
    SELECT_QUIZZES_BY_COURSE_ID("SELECT * FROM nbp24t2.nbp_quiz WHERE course_id = :course_id"),
    CREATE_QUIZ("INSERT INTO nbp24t2.nbp_quiz (course_id, start_date, deadline_date, title, description, is_active, max_points) " +
            "VALUES (:course_id, :start_date, :deadline_date, :title, :description, :is_active, :max_points)"),
    DELETE_QUIZ("DELETE FROM nbp24t2.nbp_quiz WHERE id = :id"),
    ASSIGN_QUIZ_TO_STUDENT("INSERT INTO nbp24t2.nbp_quiz_assignment (quiz_id, student_id, quiz_assignment_status_id) VALUES (:quizId, :studentId, :statusId)"),
    REMOVE_QUIZ_FROM_STUDENT("DELETE FROM nbp24t2.nbp_quiz_assignment WHERE quiz_id = :quizId and student_id = :studentId"),
    SELECT_QUIZ_STATUS_ID_BY_NAME("SELECT id FROM nbp24t2.nbp_quiz_assignment_status where name = :name"),
    SELECT_QUIZ_ASSIGNMENT("SELECT nqa.*, s.name from nbp24t2.nbp_quiz_assignment nqa, nbp24t2.nbp_quiz_assignment_status s WHERE student_id = :studentId AND quiz_id = :quizId AND nqa.quiz_assignment_status_id = s.id"),
    SUBMIT_QUIZ("UPDATE nbp24t2.nbp_quiz_assignment SET quiz_assignment_status_id = :statusId WHERE id = :quizAssignmentId"),
    SELECT_QUIZ_ID_BY_ASSIGNMENT("SELECT quiz_id FROM nbp24t2.nbp_quiz_assignment WHERE id = :quizAssignmentId"),
    UPDATE_QUIZ_POINTS("UPDATE nbp24t2.nbp_quiz_assignment SET total_points = :totalPoints WHERE id = :quizAssignmentId"),
    SELECT_QUIZ_RESULTS("SELECT s.first_name, s.last_name, s.email, qa.total_points FROM nbp.nbp_user s, nbp24t2.nbp_quiz_assignment qa " +
            "WHERE qa.student_id = s.id AND qa.quiz_assignment_status_id = :statusId AND qa.quiz_id = :quizId");


    private final String query;
    QuizQueries(String query){
        this.query = query;
    }
    public String getQuery(){
        return this.query;
    }
}
