package com.s4m.jee.servletdemo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Displays the email, media and computer selections submitted to the demonstration servlet.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Creates an empty instance with default values.
     */
    public HelloServlet() {
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

        String email = request.getParameter("email");
        String selectedMedia = request.getParameter("media");
        System.out.println("Choix Media : " + selectedMedia);
        String[] selectedComputers = request.getParameterValues("ordi");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Ma première page avec servlet</title></head>");
        out.println("<body>");
        out.println("<h1>Ma première page avec servlet</h1>");
        if (email != null && !email.equals("")) {
            out.println("La valeur du paramètre email est : " + escapeHtml(email));
        }
        if (selectedComputers != null) {
            out.println("<UL>");
            for (int i = 0; i < selectedComputers.length; i++) {
                out.println("<LI>" + escapeHtml(selectedComputers[i]) + "</LI>");
            }
            out.println("</UL>");
        }

        if (selectedMedia != null && !selectedMedia.equals("")) {
            out.println("<UL>");
            out.println("<LI>" + escapeHtml(selectedMedia) + "</LI>");
            out.println("</UL>");
        }
        out.println("<h2 align=‘center’><font color = ‘red’>Hello World !!!!!!</font></h2>");
        out.println("</body>");
        out.println("</html>");
    }

    /** Encodes untrusted values before inserting them into HTML text. */
    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    /**
     * Delegates POST requests to the GET handler.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @throws ServletException if request processing fails
     * @throws IOException if request processing fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
