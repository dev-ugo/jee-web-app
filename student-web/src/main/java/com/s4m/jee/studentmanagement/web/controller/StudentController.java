package com.s4m.jee.studentmanagement.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.s4m.jee.studentmanagement.web.handler.StudentRequestHandler;

/**
 * Dispatches student form actions to request handlers and forwards to the selected JSP.
 */
@WebServlet({"/students", "/students/new", "/students/search", "/students/delete", "/students/update"})
public class StudentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Creates an empty instance with default values.
     */
    public StudentController() {
        super();
    }

    /**
     * Handles the request and writes or forwards the response.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @throws ServletException if request processing fails
     * @throws IOException if request processing fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String viewPath;
        switch (request.getServletPath()) {
            case "/students":
                viewPath = "/WEB-INF/views/student-list-form.jsp";
                break;
            case "/students/new":
                viewPath = "/WEB-INF/views/student-create.jsp";
                break;
            case "/students/search":
                viewPath = "/WEB-INF/views/student-search.jsp";
                break;
            default:
                viewPath = "/WEB-INF/views/error.jsp";
        }
        forward(request, response, viewPath);
    }

    /**
     * Processes the form submitted to the selected student route.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @throws ServletException if request processing fails
     * @throws IOException if forwarding fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String viewPath;
        switch (request.getServletPath()) {
            case "/students":
                viewPath = StudentRequestHandler.processFindAllStudents(request);
                break;
            case "/students/new":
                viewPath = StudentRequestHandler.processAddStudent(request);
                break;
            case "/students/search":
                viewPath = StudentRequestHandler.processFindStudentByNumber(request);
                break;
            case "/students/delete":
                viewPath = StudentRequestHandler.processDeleteStudent(request);
                break;
            case "/students/update":
                viewPath = StudentRequestHandler.processUpdateStudent(request);
                break;
            default:
                viewPath = "/WEB-INF/views/error.jsp";
        }
        forward(request, response, viewPath);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String viewPath)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher(viewPath).forward(request, response);
    }

}
