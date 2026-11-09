package com.s4m.jee.studentmanagement.web.handler;

import javax.servlet.http.HttpServletRequest;

import com.s4m.jee.studentmanagement.domain.Student;
import com.s4m.jee.studentmanagement.persistence.StudentDao;
import com.s4m.jee.studentmanagement.result.OperationStatus;
import com.s4m.jee.studentmanagement.result.StudentListResult;
import com.s4m.jee.studentmanagement.result.StudentResult;

/**
 * Processes student form parameters and prepares request attributes for JSP views.
 * Delete and update actions currently return the error view.
 */
public class StudentRequestHandler {

    /**
     * Creates an instance; operations use the existing static API.
     */
    public StudentRequestHandler() {
    }

    /**
     * Validates form values and attempts to add a student after checking for duplicates.
     *
     * @param request the incoming HTTP request
     * @return the JSP path to forward to
     */
    public static String processAddStudent(HttpServletRequest request) {

        String viewPath = "/WEB-INF/views/error.jsp";
        String confirmationMessage = "not added";
        String number = request.getParameter("numelev");

        try {
            if (number == null || number.trim().isEmpty() || number.length() > 255
                    || request.getParameter("nomelev") == null
                    || request.getParameter("nomelev").trim().isEmpty()
                    || request.getParameter("nomelev").length() > 255
                    || (request.getParameter("adresselev") != null && request.getParameter("adresselev").length() > 1000)
                    || Integer.parseInt(request.getParameter("agelev")) < 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            request.setAttribute("txterro", "Numero, nom et age valide obligatoires.");
            return "/WEB-INF/views/student-create.jsp";
        }
        StudentResult existingStudent = StudentDao.findByNumber(number);

        if (existingStudent.getActionCode() == OperationStatus.EMPTY_RESULT) {
            String name = request.getParameter("nomelev");
            int age = Integer.parseInt(request.getParameter("agelev"));

            String address = request.getParameter("adresselev");

            Student newStudent = new Student(number, 0, name, age, address);
            int code = StudentDao.add(newStudent);
            System.out.println("COde de l'op�ration : " + code);
            if (code == 1) {
                confirmationMessage = "Student " + newStudent.getName() + " added with success !!";
            }
            request.setAttribute("txtconfirmationadd", confirmationMessage);
            viewPath = "/WEB-INF/views/student-created.jsp";
        } else {
            request.setAttribute("txterro", existingStudent.getActionCode() == OperationStatus.SUCCESS_RESULT ? "Erreur Eleve Existant" : "Base de donnees indisponible : verifier la configuration.");
            viewPath = "/WEB-INF/views/student-create.jsp";
        }
        return viewPath;
    }

    /**
     * Looks up the submitted student number and prepares the result message.
     *
     * @param request the incoming HTTP request
     * @return the JSP path to forward to
     */
    public static String processFindStudentByNumber(HttpServletRequest request) {
        String viewPath = "/WEB-INF/views/error.jsp";
        String studentNumber = request.getParameter("numelev");
        String resultMessage = "L'eleve ("+ studentNumber + ")not found";
        StudentResult result = StudentDao.findByNumber(studentNumber);
        if (result.getActionCode() == OperationStatus.SUCCESS_RESULT && result.getStudent() != null && result.getStudent().getNumber().equals(studentNumber)) {
            resultMessage = "L'eleve ("+ result.getStudent().getNumber() + "," +  result.getStudent().getName() +") is found";
        }
        if (result.getActionCode() < 0) return "/WEB-INF/views/error.jsp";
        request.setAttribute("txtresult", resultMessage);
        viewPath = "/WEB-INF/views/student-search-result.jsp";
        return viewPath;
    }

    /**
     * Returns the error view for the currently unsupported delete action.
     *
     * @param request the incoming HTTP request
     * @return the JSP path to forward to
     */
    public static String processDeleteStudent(HttpServletRequest request) {
        return "/WEB-INF/views/error.jsp";
    }

    /**
     * Loads students and stores the list in the request, or null when it is empty.
     *
     * @param request the incoming HTTP request
     * @return the JSP path to forward to
     */
    public static String processFindAllStudents(HttpServletRequest request) {
        String viewPath = "/WEB-INF/views/error.jsp";
        StudentListResult studentsResult = StudentDao.findAll();
        if (studentsResult.getActionCode() < 0) return "/WEB-INF/views/error.jsp";
        if (studentsResult.getActionCode() != OperationStatus.EMPTY_RESULT) {
            request.setAttribute("allelevs", studentsResult.getStudents());
        } else {
            request.setAttribute("allelevs", null);
        }

        viewPath = "/WEB-INF/views/student-list.jsp";
        return viewPath;
    }

    /**
     * Returns the error view for the currently unsupported update action.
     *
     * @param request the incoming HTTP request
     * @return the JSP path to forward to
     */
    public static String processUpdateStudent(HttpServletRequest request) {
        return "/WEB-INF/views/error.jsp";
    }
}
