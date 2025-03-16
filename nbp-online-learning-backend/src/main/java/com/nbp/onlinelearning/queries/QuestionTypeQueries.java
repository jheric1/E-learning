package com.nbp.onlinelearning.queries;

public enum QuestionTypeQueries {
    SELECT_QUESTION_TYPE_BY_NAME("SELECT id FROM nbp24t2.nbp_question_type where name = :name");

    private final String query;

    QuestionTypeQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
