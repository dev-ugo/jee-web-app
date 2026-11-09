package com.s4m.jee.studentmanagement.persistence;

import java.util.Calendar;
import java.util.UUID;
import com.s4m.jee.studentmanagement.domain.Student;
import com.s4m.jee.studentmanagement.persistence.StudentDao;
import com.s4m.jee.studentmanagement.result.OperationStatus;

/**
 * Exercises student data access against the configured database using a unique test number.
 */
public class StudentDaoSmokeTest {
    /**
     * Throws an assertion error when a smoke test condition fails.
     *
     * @param value whether the check passed
     * @param message the assertion failure message
     */
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    /**
     * Runs the database integration checks and removes the generated student in a finally block.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        String id = "TEST_" + UUID.randomUUID().toString();
        String name = "O'Neil_" + id;
        try {
            check(StudentDao.findByNumber(id).getActionCode() == 0, "Absent");
            check(StudentDao.add(new Student(id, 0, name, 22, "Rue de l'ecole")) == 1, "Ajout");
            check(name.equals(StudentDao.findByNumber(id).getStudent().getName()), "Recherche numero");
            check(StudentDao.add(new Student(id, 0, name, 22, "Test")) == OperationStatus.GENERAL_ERROR, "Doublon");
            check(StudentDao.findByName(name).getStudents().size() == 1, "Recherche nom");
            check(StudentDao.updateAddressByNumber(id, "Paris") == 1, "Adresse");
            check("Paris".equals(StudentDao.findByNumber(id).getStudent().getAddress()), "Lecture adresse");
            check(StudentDao.updateRoomNumberByNumber(id, 987654) == 1, "Chambre");
            check(StudentDao.findByRoomNumber(987654).getStudents().stream().anyMatch(e -> id.equals(e.getNumber())), "Recherche chambre");
            check(StudentDao.findByBirthYear(Calendar.getInstance().get(Calendar.YEAR) - 22)
                .getStudents().stream().anyMatch(e -> id.equals(e.getNumber())), "Annee naissance");
            check(StudentDao.findAll().getStudents().stream().anyMatch(e -> id.equals(e.getNumber())), "Liste");
            check(StudentDao.deleteByNumber(id) == 1, "Suppression");
            check(StudentDao.deleteByNumber(id) == 0, "Suppression absent");
            System.out.println("OK : tous les parcours DAO");
        } finally {
            StudentDao.deleteByNumber(id);
        }
    }
}
