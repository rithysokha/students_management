package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.model.StudentModel;
import com.student.student_management.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    private Optional<StudentModel> findStudentById(Long id){
        return studentRepository.findById(id);
    }

    public ApiResponse<List<StudentModel>> getAllStudents() {
        return new ApiResponse<>("All students ", studentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<StudentModel> getOneStudentById(Long id) {
        Optional<StudentModel> studenOptional = findStudentById(id);
        return studenOptional.map(studentModel
                -> new ApiResponse<>("Student found", studentModel, HttpStatus.OK)).orElseGet(()
                -> new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND));
    }

    public ApiResponse<StudentModel> createStudent(StudentModel studentBody) {
        StudentModel studentResponse;
        try{
            studentBody.setCreatedAt(LocalDateTime.now());
            studentResponse = studentRepository.save(studentBody);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
        return new ApiResponse<>("Student created", studentResponse, HttpStatus.CREATED);
    }

    public ApiResponse<String> deleteStudentById(Long id) {
        Optional<StudentModel> studentOptional = findStudentById(id);
        if(studentOptional.isEmpty())
            return new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND);

        StudentModel studentResponse =studentOptional.get();
        studentResponse.setDeletedAt(LocalDateTime.now());
        studentRepository.save(studentResponse);
        return new ApiResponse<>("Student deleted", null, HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<StudentModel> updateStudentById(Long id, StudentModel studentBody) {
        Optional<StudentModel> studentOptional = findStudentById(id);
        if(studentOptional.isEmpty())
            return new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND);
        StudentModel studentResponse =studentOptional.get();
        if(studentBody.getAddress()!= null && !studentBody.getAddress().isEmpty()){
            studentResponse.setAddress(studentBody.getAddress());
        }
        if(studentBody.getFirstName() != null && !studentBody.getFirstName().isEmpty()){
            studentResponse.setFirstName(studentBody.getFirstName());
        }
        if(studentBody.getLastName() != null && !studentBody.getLastName().isEmpty()){
            studentResponse.setLastName(studentBody.getLastName());
        }
        if(studentBody.getDateOfBirth() != null){
            studentResponse.setDateOfBirth(studentBody.getDateOfBirth());
        }
        studentResponse.setUpdatedAt(LocalDateTime.now());
        return new ApiResponse<>("Student updated",studentRepository.save(studentResponse), HttpStatus.OK);
    }
}
