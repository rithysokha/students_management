package com.student.student_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private int id;
    private String name;
    private String email;
    private String imgUrl;
    private String address;
    private String phone;
}
