package com.s4m.jee.studentmanagement.result;

import java.util.ArrayList;

import com.s4m.jee.studentmanagement.domain.Student;

/**
 * Carries a student list and an operation status. The default payload is null and status is zero.
 */
public class StudentListResult {
    private ArrayList<Student> students;
    private int actionCode;

    /**
     * Creates an instance with the supplied values.
     *
     * @param students the student list, possibly null
     * @param actionCode the operation status defined by OperationStatus
     */
    public StudentListResult(ArrayList<Student> students, int actionCode) {
        super();
        this.students = students;
        this.actionCode = actionCode;
    }

    /**
     * Creates an empty instance with default values.
     */
    public StudentListResult() {
        super();
    }

    /**
     * Returns the students.
     *
     * @return the current students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Sets the students.
     *
     * @param students the student list, possibly null
     */
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
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
