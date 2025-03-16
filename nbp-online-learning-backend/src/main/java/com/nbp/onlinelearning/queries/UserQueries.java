package com.nbp.onlinelearning.queries;

public enum UserQueries {
    SELECT_ALL_USERS("SELECT u.*, r.name FROM nbp.nbp_user u, nbp.nbp_role r WHERE u.role_id = r.id"),
    SELECT_ALL_STUDENTS("SELECT u.*, r.name FROM nbp.nbp_user u, nbp.nbp_role r WHERE u.role_id = r.id AND r.name = 'STUDENT'"),
    SELECT_USER_BY_USERNAME("SELECT u.*, r.name FROM nbp.nbp_user u, nbp.nbp_role r WHERE u.role_id = r.id and u.username = :username"),
    SELECT_USER_BY_EMAIL("SELECT u.*, r.name FROM nbp.nbp_user u, nbp.nbp_role r WHERE u.role_id = r.id and u.email = :email"),
    SELECT_USER_BY_ID("SELECT u.*, r.name FROM nbp.nbp_user u, nbp.nbp_role r WHERE u.role_id = r.id and u.id = :id"),
    INSERT_USER("INSERT INTO nbp.nbp_user (first_name, last_name, email, password, username, phone_number, birth_date, role_id) " +
            "VALUES (:first_name, :last_name, :email, :password, :username, :phone_number, :birth_date, :role_id)"),
    UPDATE_USER_WITHOUT_ROLE("UPDATE nbp.nbp_user SET first_name = :first_name, last_name = :last_name, email = :email, password = :password," +
            "username = :username, phone_number= :phone_number, birth_date = :birth_date WHERE id = :id"),
    DELETE_USER("DELETE FROM nbp.nbp_user WHERE id = :id"),
    SELECT_ALL_INSTRUCTORS("SELECT u.*, r.name FROM nbp.nbp_user u, nbp.nbp_role r WHERE u.role_id = r.id AND r.name = 'INSTRUCTOR'"),
    SELECT_STUDENTS_FROM_COURSE("SELECT u.*, r.name FROM nbp.nbp_user u, nbp24t2.nbp_course_student cs, nbp.nbp_role r WHERE u.role_id = r.id AND cs.student_id = u.id AND cs.course_id = :courseId"),
    SELECT_LATEST_ACTIVITY("SELECT * FROM nbp.NBP_LOG ORDER BY date_time desc FETCH FIRST 50 ROWS ONLY");

    private final String query;

    UserQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

}

