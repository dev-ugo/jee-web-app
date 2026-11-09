package com.s4m.jee.studentmanagement.web.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Adds browser protections and validates session tokens before processing forms. */
@WebFilter("/*")
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest incoming, ServletResponse outgoing, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) incoming;
        HttpServletResponse response = (HttpServletResponse) outgoing;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Security-Policy", "default-src 'self'; style-src 'self'; "
                + "img-src 'self'; script-src 'none'; object-src 'none'; base-uri 'none'; "
                + "frame-ancestors 'none'; form-action 'self'");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("Referrer-Policy", "same-origin");
        response.setHeader("Cache-Control", "no-store");

        if ("POST".equals(request.getMethod())) {
            HttpSession session = request.getSession(false);
            String expected = session == null ? null : (String) session.getAttribute("csrfToken");
            String submitted = request.getParameter("csrfToken");
            if (expected == null || submitted == null || !MessageDigest.isEqual(
                    expected.getBytes(StandardCharsets.UTF_8), submitted.getBytes(StandardCharsets.UTF_8))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Formulaire expire : rechargez la page.");
                return;
            }
        } else if ("GET".equals(request.getMethod())) {
            HttpSession session = request.getSession();
            synchronized (session) {
                if (session.getAttribute("csrfToken") == null) {
                    session.setAttribute("csrfToken", UUID.randomUUID().toString());
                }
            }
        }
        chain.doFilter(request, response);
    }
}
