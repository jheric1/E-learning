package com.nbp.onlinelearning.queries;

public enum LessonQueries {

    SELECT_ALL_LESSONS("SELECT l.*, c.id, t.name FROM nbp24t2.nbp_lesson l, nbp24t2.nbp_course c, nbp24t2.nbp_lesson_type t WHERE l.course_id = c.id and t.id=l.lesson_type_id"),
    SELECT_LESSONS_BY_COURSE_ID("SELECT l.*, t.name FROM nbp24t2.nbp_lesson l, nbp24t2.nbp_lesson_type t WHERE l.course_id = :course_id and t.id=l.lesson_type_id"),
    SELECT_LESSONS_BY_ID("SELECT l.*, c.id, t.name FROM nbp24t2.nbp_lesson l, nbp24t2.nbp_course c, nbp24t2.nbp_lesson_type t WHERE l.id = :id and l.course_id = c.id and t.id=l.lesson_type_id"),
    INSERT_LESSON("INSERT INTO nbp24t2.nbp_lesson (lesson_data, url, start_date, end_date, is_active, title, lesson_type_id, course_id) VALUES (:lesson_data, :url, :start_date, :end_date, :is_active, :title, :lesson_type_id, :course_id)"),
    DELETE_LESSON("DELETE FROM nbp24t2.nbp_lesson where id = :id");

    private final String query;
    LessonQueries(String query){
        this.query=query;
    }
    public String getQuery() {
        return this.query;
    }
}
