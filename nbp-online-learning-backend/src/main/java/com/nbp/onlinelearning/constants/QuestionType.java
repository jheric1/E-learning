package com.nbp.onlinelearning.constants;

public enum QuestionType {
    INPUT("INPUT"),
    SELECT("SELECT"),
    MULTISELECT("MULTISELECT"),
    ESSAY("ESSAY");

    private final String questionType;

    QuestionType(final String questionType){this.questionType=questionType; }
    public String getQuestionType() {
        return this.questionType;
    }

    public static boolean containsQuestionType(String type) {
        for (QuestionType questionType : QuestionType.class.getEnumConstants()) {
            if (questionType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
