package com.nbp.onlinelearning.queries;

public enum HomeworkQueries {
    SELECT_ALL_HOMEWORKS("SELECT * FROM nbp24t2.nbp_homework"),
    SELECT_HOMEWORK_BY_ID("SELECT * FROM nbp24t2.nbp_homework WHERE id = :id"),
    CREATE_HOMEWORK("INSERT INTO nbp24t2.nbp_homework (course_id, max_points, start_date, deadline_date, title, description) VALUES (:course_id, :max_points, :start_date, :deadline_date, :title, :description)"),
    SELECT_HOMEWORKS_BY_COURSE_ID("SELECT * FROM nbp24t2.nbp_homework WHERE course_id = :course_id");

    private final String query;

    HomeworkQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
