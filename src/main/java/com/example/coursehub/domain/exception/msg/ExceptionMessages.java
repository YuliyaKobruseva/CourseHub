package com.example.coursehub.domain.exception.msg;

public class ExceptionMessages {
    public static final String COURSE_NOT_FOUND = "Course not found";
    public static final String STUDENT_NOT_FOUND = "Student not found";
    public static final String INVALID_COURSE_ID = "Invalid course ID";
    public static final String DUPLICATE_STUDENT = "Student already enrolled";
    public static final String UNAUTHORIZED_ACCESS = "You do not have permission for this operation";

    private ExceptionMessages() {
        // Prevent instantiation
    }
}
