package com.student.student_management.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream cachedBody;
    private final PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        this.cachedBody = new ByteArrayOutputStream();
        this.writer = new PrintWriter(cachedBody);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new CachedBodyServletOutputStream(cachedBody);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
        super.flushBuffer();
    }

    public byte[] getCachedBody() {
        return cachedBody.toByteArray();
    }

    private static class CachedBodyServletOutputStream extends ServletOutputStream {

        private final ByteArrayOutputStream cachedBody;

        public CachedBodyServletOutputStream(ByteArrayOutputStream cachedBody) {
            this.cachedBody = cachedBody;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // No implementation needed
        }

        @Override
        public void write(int b) throws IOException {
            cachedBody.write(b);
        }
    }
}