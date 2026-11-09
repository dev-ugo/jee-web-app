package com.s4m.jee.studentmanagement.result;

/**
 * Defines status codes returned by student data access operations.
 */
public class OperationStatus {

    /**
     * Creates an instance; operations use the existing static API.
     */
    public OperationStatus() {
    }
    /** The operation succeeded and returned data. */
    public static final int SUCCESS_RESULT = 1;
    /** The query completed without matching records. */
    public static final int EMPTY_RESULT = 0;
    /** A database error occurred. */
    public static final int SERVER_ERROR = -1;
    /** A general error or duplicate student key occurred. */
    public static final int GENERAL_ERROR = -2;
}
