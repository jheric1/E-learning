package com.nbp.onlinelearning.constants;

public enum LessonType {

    TEXT("TEXT"),
    EXTERNAL("EXTERNAL");

    private final String lessonType;

    LessonType(final String lessonType){this.lessonType=lessonType; }
    public String getLessonType() {
        return this.lessonType;
    }

    public static boolean containsLessonType(String type) {
        for (LessonType lessonType : LessonType.class.getEnumConstants()) {
            if (lessonType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
