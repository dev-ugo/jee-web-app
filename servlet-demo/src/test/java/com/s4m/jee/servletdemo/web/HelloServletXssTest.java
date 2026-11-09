package com.s4m.jee.servletdemo.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Proxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Standalone regression test; requires only the Servlet API. */
public class HelloServletXssTest {
    public static void main(String[] args) throws Exception {
        for (boolean post : new boolean[] { false, true }) {
            verifyResponse(post, "<script>alert(\"XSS\")</script>&'é",
                    "&lt;script&gt;alert(&quot;XSS&quot;)&lt;/script&gt;&amp;&#39;é");
            verifyResponse(post, "alice@example.com", "alice@example.com");
            verifyResponse(post, null, null);
        }
        System.out.println("HelloServlet XSS tests passed (GET and POST).");
    }

    private static void verifyResponse(boolean post, String input, String expected) throws Exception {
        StringWriter body = new StringWriter();
        String[] contentType = { null };
        HttpServletRequest request = (HttpServletRequest) Proxy.newProxyInstance(
                HelloServletXssTest.class.getClassLoader(), new Class<?>[] { HttpServletRequest.class },
                (proxy, method, args) -> {
                    if (method.getName().equals("getParameter")) {
                        return input;
                    }
                    if (method.getName().equals("getParameterValues")) {
                        return input == null ? null : new String[] { input, input };
                    }
                    throw new AssertionError("Unexpected request call: " + method.getName());
                });
        HttpServletResponse response = (HttpServletResponse) Proxy.newProxyInstance(
                HelloServletXssTest.class.getClassLoader(), new Class<?>[] { HttpServletResponse.class },
                (proxy, method, args) -> {
                    if (method.getName().equals("setContentType")) {
                        contentType[0] = (String) args[0];
                        return null;
                    }
                    if (method.getName().equals("getWriter")) {
                        if (!"text/html;charset=UTF-8".equals(contentType[0])) {
                            throw new AssertionError("HTML UTF-8 must be set before opening the writer");
                        }
                        return new PrintWriter(body);
                    }
                    throw new AssertionError("Unexpected response call: " + method.getName());
                });
        HelloServlet servlet = new HelloServlet();
        if (post) {
            servlet.doPost(request, response);
        } else {
            servlet.doGet(request, response);
        }
        String html = body.toString();
        if (expected != null) {
            if (!html.contains("La valeur du paramètre email est : " + expected)
                    || html.split(java.util.regex.Pattern.quote("<LI>" + expected + "</LI>"), -1).length != 4) {
                throw new AssertionError("Every submitted value must be HTML encoded: " + html);
            }
        } else if (html.contains("<LI>") || html.contains("La valeur du paramètre email")) {
            throw new AssertionError("Missing parameters should not produce values");
        }
    }
}
