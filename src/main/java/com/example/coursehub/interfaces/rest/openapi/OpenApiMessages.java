package com.example.coursehub.interfaces.rest.openapi;

public class OpenApiMessages {
    // General
    public static final String OK = "Operation completed successfully";
    public static final String CREATED = "Resource created successfully";
    public static final String NO_CONTENT = "Resource deleted successfully";
    public static final String NOT_FOUND = "Resource not found";
    public static final String BAD_REQUEST = "Invalid input";

    // Courses
    public static final String COURSE_TAG = "Courses";
    public static final String COURSE_TAG_DESCRIPTION = "Operations related to course management";
    public static final String COURSE_GET_ALL = "Get all courses";
    public static final String COURSE_GET_BY_ID = "Get a course by ID";
    public static final String COURSE_CREATE = "Create a new course";
    public static final String COURSE_UPDATE = "Update an existing course";
    public static final String COURSE_DELETE = "Delete a course";

    // Students
    public static final String STUDENT_TAG = "Students";
    public static final String STUDENT_TAG_DESCRIPTION = "Operations related to student management";
    public static final String STUDENT_GET_ALL = "Get all students";
    public static final String STUDENT_GET_BY_ID = "Get a student by ID";
    public static final String STUDENT_CREATE = "Create a new student and assign to a course";
    public static final String STUDENT_UPDATE = "Update an existing student";
    public static final String STUDENT_DELETE = "Delete a student";

    private OpenApiMessages() {}
}
