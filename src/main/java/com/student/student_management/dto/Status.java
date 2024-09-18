package com.student.student_management.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Status {
    SUCCESS("success"),
    FAIL("fail");

    private final String value;

    Status(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}