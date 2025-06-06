package com.example.coursehub.domain.exception.msg;

public class ExceptionMessages {
    public static final String COURSE_NOT_FOUND = "Course not found";
    public static final String STUDENT_NOT_FOUND = "Student not found";
    public static final String STUDENT_ALREADY_ENROLLED = "Student already enrolled";
    public static final String UNAUTHORIZED_ACCESS = "You do not have permission for this operation";
    public static final String INVALID_END_DATE = "Course end date cannot be before start date";

    private ExceptionMessages() {
        // Prevent instantiation
    }
}
