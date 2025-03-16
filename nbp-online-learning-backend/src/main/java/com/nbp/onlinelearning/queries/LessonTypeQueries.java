package com.nbp.onlinelearning.queries;

public enum LessonTypeQueries {
    SELECT_LESSON_TYPE_BY_NAME("SELECT id FROM nbp24t2.nbp_lesson_type where name = :name");
    private final String query;
    LessonTypeQueries(String query){
        this.query=query;
    }
    public String getQuery() {
        return this.query;
    }
}
