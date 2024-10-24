package com.student.student_management.dto;

import java.util.List;

public record CreateAndUpdateCourse (String courseName, Float maxCredit, List<Integer> majorIds){
}
