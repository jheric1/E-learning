package com.nbp.onlinelearning.constants;

public enum QuizAssignmentStatus {

    COMPLETED("COMPLETED"),
    GRADED("GRADED"),
    STARTED("STARTED"),
    NOT_STARTED("NOT_STARTED");
    private final String status;

    QuizAssignmentStatus(final String quizAssignmentStatus) {
        this.status = quizAssignmentStatus;
    }

    public String getStatus() {
        return this.status;
    }
}
