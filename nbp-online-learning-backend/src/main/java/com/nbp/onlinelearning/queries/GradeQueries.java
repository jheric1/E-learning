package com.nbp.onlinelearning.queries;

public enum GradeQueries {
    SELECT_ALL_GRADES("SELECT * FROM nbp24t2.nbp_grade"),
    SELECT_GRADE_BY_DESCRIPTION("SELECT * FROM nbp24t2.nbp_grade WHERE description = :description"),
    SELECT_GRADE_BY_VALUE("SELECT * FROM nbp24t2.nbp_grade WHERE value = :value");
    private final String query;
    GradeQueries(String query){
        this.query = query;
    }
    public String getQuery(){
        return this.query;
    }
}
