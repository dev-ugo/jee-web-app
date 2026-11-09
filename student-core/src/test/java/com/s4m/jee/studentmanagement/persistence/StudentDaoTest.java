package com.s4m.jee.studentmanagement.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.s4m.jee.studentmanagement.domain.Student;
import com.s4m.jee.studentmanagement.persistence.StudentDao;
import com.s4m.jee.studentmanagement.result.OperationStatus;
import com.s4m.jee.studentmanagement.result.StudentResult;

/**
 * Contains legacy JUnit integration tests. Some test methods remain unimplemented.
 */
class StudentDaoTest {
    @BeforeEach
    /**
     * Sets the up.
     *
     * @throws Exception if request processing fails
     */
    public void setUp() throws Exception {
        int insert = 1;
        assertEquals(insert, StudentDao.add(new Student("AGUE001", 0, "AGUEH MAX", 40, "18 Rue Labat 75018 Paris")));
        assertEquals(insert, StudentDao.add(new Student("MOHAMED002", 0, "MOHAMED LAHBIB", 22, "19 Rue Le Monde Paris")));
        assertEquals(insert,
                StudentDao.add(new Student("KAMTO005", 0, "KAMTO Diogène", 50, "54 Rue des Ebisoires 78300 Poissy")));
        assertEquals(insert, StudentDao
                .add(new Student("NOED004", 0, "SCHMIDT Noe", 52, "79 Rue des Poules 75015 Paris")));
        assertEquals(insert,
                StudentDao.add(new Student("MARI003", 0, "MARIAM BELHADJ", 30, "12 Rue du louvre 75013 Paris")));
        assertEquals(insert, StudentDao
                .add(new Student("TAHAE002", 0, "TAHA RIDENE", 30, "12 Rue des Chantiers 78000 Versailles")));

    }

    @AfterEach
    public void tearDown() throws Exception {
        int delete = 1;
        assertEquals(delete, StudentDao.deleteByNumber("AGUE001"));
        assertEquals(delete, StudentDao.deleteByNumber("MOHAMED002"));
        assertEquals(delete, StudentDao.deleteByNumber("KAMTO005"));
        assertEquals(delete, StudentDao.deleteByNumber("NOED004"));
        assertEquals(delete, StudentDao.deleteByNumber("MARI003"));
        assertEquals(delete, StudentDao.deleteByNumber("TAHAE002"));
        System.out.println("Eleve Supprimé ou pas : " + delete);

    }

    @Test
    void testFindByNumber() {
        StudentResult e_ref = new StudentResult(new Student("AGUE001", 0, "AGUEH MAX", 40, "18 Rue Labat 75018 Paris"),1);
        StudentResult e = new StudentResult();
        e = StudentDao.findByNumber("AGUE001");
        e.getStudent().print();
        assertEquals(e_ref.getStudent().getAge(), e.getStudent().getAge());
        assertEquals(e_ref.getStudent().getAddress(), e.getStudent().getAddress());
        assertEquals(e_ref.getStudent().getName(), e.getStudent().getName());
        assertEquals(e_ref.getActionCode(), e.getActionCode());
        e= StudentDao.findByNumber("NOTEXIST000");
        assertEquals(OperationStatus.EMPTY_RESULT, e.getActionCode());

    }

    @Test
    void testFindByName() {
        fail("Not yet implemented");
    }

    @Test
    void testFindByRoomNumber() {
        fail("Not yet implemented");
    }

    @Test
    void testDeleteByNumber() {
        int nb = StudentDao.deleteByNumber("NOEXISTING");
        assertEquals(0, nb);
        int insert = 1;
        assertEquals(insert,
                StudentDao.add(new Student("GIOVANNI008", 0, "GIOVANNI FERRETTI", 22, "18 Rue des acacias Geneve")));
        nb = StudentDao.deleteByNumber("GIOVANNI008");
        assertEquals(1, nb);
    }

    @Test
    void testUpdateAddressByNumber() {
        fail("Not yet implemented");
    }

    @Test
    void testUpdateRoomNumberByNumber() {
        fail("Not yet implemented");
    }

    @Test
    void testAdd() {
        int insert_true = 1;
        int delete_true = 1;
        assertEquals(insert_true,
                StudentDao.add(new Student("JULIAE003", 0, "JULIA VARONE", 23, "18 Rue des acacias Geneve")));
        assertEquals(delete_true, StudentDao.deleteByNumber("JULIAE003"));

    }

    @Test
    void testFindByBirthYear() {
        fail("Not yet implemented");
    }

    @Test
    void testFindAll() {
        fail("Not yet implemented");
    }
}
