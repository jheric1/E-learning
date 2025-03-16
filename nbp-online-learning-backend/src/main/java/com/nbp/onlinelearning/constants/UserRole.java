package com.nbp.onlinelearning.constants;

public enum UserRole {
    STUDENT("STUDENT"),
    INSTRUCTOR("INSTRUCTOR"),
    ADMINISTRATOR("ADMINISTRATOR"),
    OTHER("OTHER");

    private final String role;

    UserRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public static boolean containsRole(String role) {
        for (UserRole userRole : UserRole.class.getEnumConstants()) {
            if (userRole.name().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
