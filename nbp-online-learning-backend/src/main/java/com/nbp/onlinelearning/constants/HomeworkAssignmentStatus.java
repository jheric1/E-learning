package com.nbp.onlinelearning.constants;

public enum HomeworkAssignmentStatus {

    COMPLETED("COMPLETED"), GRADED("GRADED"), NOT_SUBMITTED("NOT_SUBMITTED");
    private final String status;

    HomeworkAssignmentStatus(final String homeworkAssignmentStatus) {
        this.status = homeworkAssignmentStatus;
    }

    public String getStatus() {
        return this.status;
    }

    public static boolean contains(String status) {
        for (HomeworkAssignmentStatus homeworkAssignmentStatus : HomeworkAssignmentStatus.class.getEnumConstants()) {
            if (homeworkAssignmentStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
