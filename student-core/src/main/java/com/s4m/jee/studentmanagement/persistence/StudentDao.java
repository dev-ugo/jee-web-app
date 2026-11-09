package com.s4m.jee.studentmanagement.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

import com.s4m.jee.studentmanagement.domain.Student;
import com.s4m.jee.studentmanagement.result.OperationStatus;
import com.s4m.jee.studentmanagement.persistence.DatabaseConnection;
import com.s4m.jee.studentmanagement.result.StudentListResult;
import com.s4m.jee.studentmanagement.result.StudentResult;

/**
 * Provides synchronized access to student records using the shared database connection.
 * Query results carry an {@link OperationStatus} status; empty results leave the payload null.
 */
public class StudentDao {

    /**
     * Creates an instance; operations use the existing static API.
     */
    public StudentDao() {
    }

    /**
     * Finds a student by their unique number.
     *
     * @param numberToFind the unique student number to look up
     * @return the payload and status: success (1), empty (0), SQL error (-1) or general error (-2)
     */
    public static synchronized StudentResult findByNumber(String numberToFind) {
        Student student = new Student();
        ResultSet rs = null;
        StudentResult result = new StudentResult();
        result.setActionCode(OperationStatus.SUCCESS_RESULT);
        DatabaseConnection.connect();

        String sql = "SELECT num, no, nom, age, adresse FROM eleve WHERE num = ?";
        try {
            PreparedStatement query = DatabaseConnection.getConnection().prepareStatement(sql);
            DatabaseConnection.setStatement(query);
            query.setString(1, numberToFind);
            rs = query.executeQuery();

            if (rs.next()) {
                student.setNumber(rs.getString(1));
                student.setRoomNumber(rs.getInt(2));
                student.setName(rs.getString(3));
                student.setAge(rs.getInt(4));
                student.setAddress(rs.getString(5));
                result.setStudent(student);
                if (!rs.isClosed()) {
                    rs.close();
                }
            } else {
                result.setActionCode(OperationStatus.EMPTY_RESULT);
            }
        } catch (SQLException ex1) {
            result.setActionCode(OperationStatus.SERVER_ERROR);
            ex1.printStackTrace();
        } catch (Exception ex2) {
            result.setActionCode(OperationStatus.GENERAL_ERROR);
            ex2.printStackTrace();
        }

        DatabaseConnection.close();
        return result;
    }

    /**
     * Finds students whose stored name matches exactly.
     *
     * @param studentName the exact name to match
     * @return the payload and status: success (1), empty (0), SQL error (-1) or general error (-2)
     */
    public static synchronized StudentListResult findByName(String studentName) {
        ArrayList<Student> students = new ArrayList<>();
        ResultSet rs = null;
        Student student = new Student();
        StudentListResult result = new StudentListResult();

        DatabaseConnection.connect();

        String sql = "Select * from eleve where nom = '" + studentName.replace("'", "''") + "'";

        try {
            rs = DatabaseConnection.getStatement().executeQuery(sql);
            while (rs.next()) {
                student = new Student();
                student.setNumber(rs.getString(1));
                student.setRoomNumber(rs.getInt(2));
                student.setName(rs.getString(3));
                student.setAge(rs.getInt(4));
                student.setAddress(rs.getString(5));
                students.add(student);
            }
            if (!rs.isClosed()) {
                rs.close();
            }
            if (students.isEmpty()) {
                result.setActionCode(OperationStatus.EMPTY_RESULT);
            } else {
                result.setActionCode(OperationStatus.SUCCESS_RESULT);
                result.setStudents(students);
            }
        } catch (SQLException sqlException) {
            result.setActionCode(OperationStatus.SERVER_ERROR);
        } catch (Exception exception) {
            result.setActionCode(OperationStatus.GENERAL_ERROR);
        }

        DatabaseConnection.close();

        return result;
    }

    /**
     * Finds students assigned to the specified room.
     *
     * @param roomNumber the assigned room number
     * @return the payload and status: success (1), empty (0), SQL error (-1) or general error (-2)
     */
    public static synchronized StudentListResult findByRoomNumber(int roomNumber) {

        ArrayList<Student> students = new ArrayList<>();
        StudentListResult result = new StudentListResult();

        String sql = "SELECT num, no, nom, age, adresse FROM eleve where no = " + roomNumber + " ";

        try {
            DatabaseConnection.connect();

            DatabaseConnection.setResultSet(DatabaseConnection.getStatement().executeQuery(sql));

            while (DatabaseConnection.getResultSet().next()) {
                Student student = new Student();

                student.setNumber(DatabaseConnection.getResultSet().getString(1));
                student.setRoomNumber(DatabaseConnection.getResultSet().getInt(2));
                student.setName(DatabaseConnection.getResultSet().getString(3));
                student.setAge(DatabaseConnection.getResultSet().getInt(4));
                student.setAddress(DatabaseConnection.getResultSet().getString(5));
                students.add(student);
            }
            if (students.isEmpty()) {
                result.setActionCode(OperationStatus.EMPTY_RESULT);
            } else {
                result.setActionCode(OperationStatus.SUCCESS_RESULT);
                result.setStudents(students);
            }
        } catch (SQLException sqlException) {
            result.setActionCode(OperationStatus.SERVER_ERROR);
        } catch (Exception exception) {
            result.setActionCode(OperationStatus.GENERAL_ERROR);
        }

        DatabaseConnection.close();

        return result;

    }

    /**
     * Deletes a student identified by their number.
     *
     * @param number the unique student number
     * @return the affected row count, or a negative error status
     */
    public static synchronized int deleteByNumber(String number) {
        int result = -1;

        DatabaseConnection.connect();

        String sql = "DELETE FROM eleve WHERE num = '" + number + "' ";
        try {
            result = DatabaseConnection.getStatement().executeUpdate(sql);
            System.out.println("Requete executée");
        } catch (SQLException ex) {
            result = OperationStatus.SERVER_ERROR;
            System.out.println(ex.getMessage());
        }
        System.out.println("[" + sql + "] Suppression : Valeur de result = " + result);
        DatabaseConnection.close();
        return result;
    }

    /**
     * Updates the address of a student identified by their number.
     *
     * @param number the unique student number
     * @param newAddress the replacement postal address
     * @return the affected row count, or a negative error status
     */
    public static synchronized int updateAddressByNumber(String number, String newAddress) {
        int result = -1;
        DatabaseConnection.connect();
        String sql = "UPDATE eleve SET adresse = '" + newAddress + "' WHERE num ='" + number + "' ";
        try {
            result = DatabaseConnection.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            result = -1;
        }
        System.out.println("Requete executee");
        DatabaseConnection.close();
        return result;
    }

    /**
     * Updates the room of a student identified by their number.
     *
     * @param number the unique student number
     * @param newRoomNumber the replacement room number
     * @return the affected row count, or a negative error status
     */
    public static synchronized int updateRoomNumberByNumber(String number, int newRoomNumber) {
        int result = -1;
        DatabaseConnection.connect();

        String sql = "UPDATE eleve SET no = " + newRoomNumber + " WHERE num ='" + number + "' ";
        try {
            result = DatabaseConnection.getStatement().executeUpdate(sql);
            System.out.println("Requete executee");
        } catch (SQLException ex0) {
            result = -1;

            int code = ex0.getErrorCode();
            switch (code) {
            case 1002:
                break;
            }
        } catch (Exception ex1) {
            result = OperationStatus.GENERAL_ERROR;
        }
        DatabaseConnection.close();
        return result;
    }

    /**
     * Inserts a student, storing a room number of zero as SQL NULL.
     *
     * @param newStudent the student to insert
     * @return the affected row count, -2 for a duplicate key, or -1 for another SQL error
     */
    public static synchronized int add(Student newStudent) {
        int result = -1;
        DatabaseConnection.connect();

        String sql = "INSERT INTO eleve (num, no, nom, age, adresse) VALUES (?, ?, ?, ?, ?)";
        try {
            if (DatabaseConnection.getConnection() == null) return OperationStatus.SERVER_ERROR;
            PreparedStatement query = DatabaseConnection.getConnection().prepareStatement(sql);
            DatabaseConnection.setStatement(query);
            query.setString(1, newStudent.getNumber());
            if (newStudent.getRoomNumber() == 0) query.setNull(2, Types.INTEGER);
            else query.setInt(2, newStudent.getRoomNumber());
            query.setString(3, newStudent.getName());
            query.setInt(4, newStudent.getAge());
            query.setString(5, newStudent.getAddress());
            result = query.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                result = OperationStatus.GENERAL_ERROR;
            } else {
                result = OperationStatus.SERVER_ERROR;
            }
            System.out.println(ex.getMessage());
        }
        DatabaseConnection.close();
        return result;
    }

    /**
     * Finds students whose age equals the current calendar year minus the supplied birth year.
     *
     * @param birthYear the birth year used to calculate the age to match
     * @return the payload and status: success (1), empty (0), SQL error (-1) or general error (-2)
     */
    public static synchronized StudentListResult findByBirthYear(int birthYear) {
        ArrayList<Student> students = new ArrayList<>();
        StudentListResult result = new StudentListResult();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        int studentAge = year - birthYear;
        String sql = "SELECT num, no, nom, age, adresse FROM eleve WHERE age =" + studentAge + " ";

        DatabaseConnection.connect();

        try {
            DatabaseConnection.setResultSet(DatabaseConnection.getStatement().executeQuery(sql));
            while (DatabaseConnection.getResultSet().next()) {

                Student student = new Student();

                student.setNumber(DatabaseConnection.getResultSet().getString(1));
                student.setRoomNumber(DatabaseConnection.getResultSet().getInt(2));
                student.setName(DatabaseConnection.getResultSet().getString(3));
                student.setAge(DatabaseConnection.getResultSet().getInt(4));
                student.setAddress(DatabaseConnection.getResultSet().getString(5));
                students.add(student);
            }
            if (students.isEmpty()) {
                result.setActionCode(OperationStatus.EMPTY_RESULT);
            } else {
                result.setActionCode(OperationStatus.SUCCESS_RESULT);
                result.setStudents(students);
            }
        } catch (SQLException sqlException) {
            result.setActionCode(OperationStatus.SERVER_ERROR);
        } catch (Exception exception) {
            result.setActionCode(OperationStatus.GENERAL_ERROR);
        }

        DatabaseConnection.close();

        return result;
    }

    /**
     * Loads all students without imposing a sort order.
     *
     * @return the payload and status: success (1), empty (0), SQL error (-1) or general error (-2)
     */
    public static synchronized StudentListResult findAll() {
        ArrayList<Student> students = new ArrayList<>();
        StudentListResult result = new StudentListResult();
        String sql = "SELECT num, no, nom, age, adresse FROM eleve ";

        DatabaseConnection.connect();

        try {
            DatabaseConnection.setResultSet(DatabaseConnection.getStatement().executeQuery(sql));
            while (DatabaseConnection.getResultSet().next()) {

                Student student = new Student();

                student.setNumber(DatabaseConnection.getResultSet().getString(1));
                student.setRoomNumber(DatabaseConnection.getResultSet().getInt(2));
                student.setName(DatabaseConnection.getResultSet().getString(3));
                student.setAge(DatabaseConnection.getResultSet().getInt(4));
                student.setAddress(DatabaseConnection.getResultSet().getString(5));
                students.add(student);
            }

            if (students.isEmpty()) {
                result.setActionCode(OperationStatus.EMPTY_RESULT);
            } else {
                result.setActionCode(OperationStatus.SUCCESS_RESULT);
                result.setStudents(students);
            }
        } catch (SQLException sqlException) {
            result.setActionCode(OperationStatus.SERVER_ERROR);
        } catch (Exception exception) {
            result.setActionCode(OperationStatus.GENERAL_ERROR);
        }

        DatabaseConnection.close();

        return result;
    }
}
