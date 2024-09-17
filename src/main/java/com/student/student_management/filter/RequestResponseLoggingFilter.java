package com.student.student_management.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@WebFilter(urlPatterns = "/*")
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!httpRequest.getRequestURI().startsWith("/auth")) {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpRequest);
            CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(httpResponse);

            String requestBody = new BufferedReader(new InputStreamReader(cachedBodyHttpServletRequest.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            logger.info("Request: {} {} User: {} Body: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getUserPrincipal().getName(), requestBody);

            chain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

            String responseBody = new String(cachedBodyHttpServletResponse.getCachedBody(), StandardCharsets.UTF_8);
            logger.info("Response: {} {} Body: {}", httpResponse.getStatus(), httpRequest.getRequestURI(), responseBody);

            // Write the cached response body back to the original response
            httpResponse.getOutputStream().write(cachedBodyHttpServletResponse.getCachedBody());
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}