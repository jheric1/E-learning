package com.nbp.onlinelearning.queries;

public enum RoleQueries {

    SELECT_ROLE_ID_BY_NAME("SELECT id FROM nbp.nbp_role where name = :name");

    private final String query;

    RoleQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
