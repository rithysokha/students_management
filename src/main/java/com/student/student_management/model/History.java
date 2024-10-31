package com.student.student_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private int id;
    private String name;
    private String emotion;
    private String created_at;
}
