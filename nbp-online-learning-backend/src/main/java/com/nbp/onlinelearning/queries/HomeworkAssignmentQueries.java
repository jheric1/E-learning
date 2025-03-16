package com.nbp.onlinelearning.queries;

public enum HomeworkAssignmentQueries {
    SELECT_ALL_HOMEWORK_ASSIGNMENTS("SELECT * FROM nbp24t2.nbp_homework_assignment"),
    FIND_HOMEWORK_ASSIGNMENT_BY_HOMEWORK_ID("SELECT h.*, s.name FROM nbp24t2.nbp_homework_assignment h, nbp24t2.nbp_homework_assignment_status s WHERE h.homework_id = :homeworkId AND h.student_id = :studentId AND h.homework_assignment_status_id = s.id"),
    SUBMIT_HOMEWORK("UPDATE nbp24t2.nbp_homework_assignment SET homework_assignment_status_id = :statusId, submit_date = :submitDate, homework_data_url = :homeworkDataUrl WHERE id = :id"),
    SELECT_HOMEWORK_STATUS_ID_BY_NAME("SELECT id FROM nbp24t2.nbp_homework_assignment_status where name = :name"),
    ASSIGN_HOMEWORK_TO_STUDENT("INSERT INTO nbp24t2.nbp_homework_assignment (homework_assignment_status_id, student_id, homework_id) VALUES (:statusId, :studentId, :homeworkId)"),
    REMOVE_HOMEWORK_FROM_STUDENT("DELETE FROM nbp24t2.nbp_homework_assignment WHERE homework_id = :homeworkId and student_id = :studentId"),
    FIND_HOMEWORK_ASSIGNMENTS_BY_HOMEWORK_ID("SELECT h.id, h.points, h.submit_date, s.name, h.homework_data_url, u.first_name, u.last_name, u.email FROM nbp24t2.nbp_homework_assignment h, nbp24t2.nbp_homework_assignment_status s, nbp.nbp_user u WHERE h.homework_id = :homeworkId AND h.homework_assignment_status_id = s.id AND u.id = h.student_id"),
    GRADE_HOMEWORK("UPDATE nbp24t2.nbp_homework_assignment SET points = :points WHERE id = :homeworkAssignmentId");



    private final String query;

    HomeworkAssignmentQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
