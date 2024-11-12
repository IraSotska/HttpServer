package com.sotska.entity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Request implements HttpServletRequest {

    private String method;
    private String requestURI;
    private String contextPath;
    private Map<String, String> parameters = new HashMap<>();
    private HttpSession session;
    private InputStream inputStream;

    private String servletPath;

    private final Map<String, String> headers = new HashMap<>();

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public Enumeration<?> getHeaderNames() {
        return java.util.Collections.enumeration(headers.keySet());
    }
    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaders(String s) {
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() {

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
            @Override
            public long transferTo(OutputStream out) throws IOException {
                return inputStream.transferTo(out);
            }
            @Override
            public int read(byte b[]) throws IOException {
                return inputStream.read(b);
            }
            @Override
            public int read(byte b[], int off, int len) throws IOException {
                return inputStream.read(b, off, len);
            }
            @Override
            public byte[] readAllBytes() throws IOException {
                return inputStream.readAllBytes();
            }
            @Override
            public byte[] readNBytes(int len) throws IOException {
                return inputStream.readNBytes(len);
            }
            @Override
            public int readNBytes(byte[] b, int off, int len) throws IOException {
                return inputStream.readNBytes(b, off, len);
            }
            @Override
            public long skip(long n) throws IOException {
                return inputStream.skip(n);
            }
            @Override
            public void skipNBytes(long n) throws IOException {
                inputStream.skipNBytes(n);
            }
        };
    }

    @Override
    public String getParameter(String name) {
        return "hello";
//                parameters.get(name);
    }

    @Override
    public Enumeration getParameterNames() {
        return java.util.Collections.enumeration(parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return "1.1";
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

}
