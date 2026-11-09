package com.s4m.jee.studentmanagement.result;

import com.s4m.jee.studentmanagement.domain.Student;

/**
 * Carries one student and an operation status. The default payload is null and status is zero.
 */
public class StudentResult {
    private Student student;
    private int actionCode;

    /**
     * Creates an instance with the supplied values.
     *
     * @param student the student payload, possibly null
     * @param actionCode the operation status defined by OperationStatus
     */
    public StudentResult(Student student, int actionCode) {
        super();
        this.student = student;
        this.actionCode = actionCode;
    }
    /**
     * Creates an empty instance with default values.
     */
    public StudentResult() {
        this(null, 0);
    }
    /**
     * Returns the student.
     *
     * @return the current student
     */
    public Student getStudent() {
        return student;
    }
    /**
     * Sets the student.
     *
     * @param student the student payload, possibly null
     */
    public void setStudent(Student student) {
        this.student = student;
    }
    /**
     * Returns the action code.
     *
     * @return the current action code
     */
    public int getActionCode() {
        return actionCode;
    }
    /**
     * Sets the action code.
     *
     * @param actionCode the operation status defined by OperationStatus
     */
    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }
}
