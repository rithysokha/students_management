package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateStudent;
import com.student.student_management.dto.IsBlackListed;
import com.student.student_management.model.ClassModel;
import com.student.student_management.model.StudentModel;
import com.student.student_management.repository.ClassRepository;
import com.student.student_management.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final WebClient webClient;
    public ApiResponse<List<StudentModel>> getAllStudents() {
        return new ApiResponse<>("All students", studentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<StudentModel> getOneStudentById(Long id) {
        Optional<StudentModel> studenOptional = studentRepository.findById(id);
        if(studenOptional.isPresent() && studenOptional.get().getDeletedAt()==null){
            return new ApiResponse<>("Student found", studenOptional.get(), HttpStatus.OK);
        }
        return new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND);
    }

    public ApiResponse<StudentModel> createStudent(CreateAndUpdateStudent studentBody) {
        try {
            if(isStudentBlackListed(studentBody).blackListed()){
                return new ApiResponse<>("Student is in black list", null, HttpStatus.OK);
            }
            StudentModel studentModel = new StudentModel();
            Optional<ClassModel> classOptional = classRepository.findById(studentBody.classId());
            studentModel.setCreatedAt(LocalDateTime.now());
            studentModel.setFirstName(studentBody.firstName());
            studentModel.setLastName(studentBody.lastName());
            studentModel.setDateOfBirth(studentBody.dateOfBirth());
            studentModel.setAddress(studentBody.address());
            if(classOptional.isEmpty() || classOptional.get().getDeletedAt() != null)
                return new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND);
            studentModel.setStudentClass(classOptional.get());
            studentModel.setPhoneNumber(studentBody.phoneNumber());
            StudentModel response = studentRepository.save(studentModel);
            return new ApiResponse<>("Student created", response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    public ApiResponse<StudentModel> deleteStudentById(Long id) {
        try {
        ApiResponse<StudentModel> studentResponse = getOneStudentById(id);
        if(studentResponse.status() != HttpStatus.OK){
            return studentResponse;
        }
        StudentModel studentRes = studentResponse.data();
        studentRes.setDeletedAt(LocalDateTime.now());
        studentRepository.save(studentRes);
        return new ApiResponse<>("Student deleted", null, HttpStatus.OK);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ApiResponse<StudentModel> updateStudentById(Long id, CreateAndUpdateStudent studentBody) {
        try {
        ApiResponse<StudentModel> studentResponse = getOneStudentById(id);
        Optional<ClassModel> classOptional = classRepository.findById(studentBody.classId());
        if(studentResponse.status() != HttpStatus.OK){
            return studentResponse;
        }
        StudentModel studentData = studentResponse.data();
        updateFieldIfNotNull(studentBody.address(), studentData::setAddress);
        updateFieldIfNotNull(studentBody.firstName(), studentData::setFirstName);
        updateFieldIfNotNull(studentBody.lastName(), studentData::setLastName);
        updateFieldIfNotNull(studentBody.dateOfBirth(), studentData::setDateOfBirth);
        if(classOptional.isEmpty())
            return new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND);
        studentData.setStudentClass(classOptional.get());
        updateFieldIfNotNull(studentBody.phoneNumber(), studentData::setPhoneNumber);
        studentData.setUpdatedAt(LocalDateTime.now());
        return new ApiResponse<>("Student updated", studentRepository.save(studentData), HttpStatus.OK);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    private <T> void updateFieldIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
            setter.accept(value);
        }
    }

    public ApiResponse<List<StudentModel>> getStudentsByClassId(Long classId) {
        return new ApiResponse<>("Students by class", studentRepository.findAllByStudentClassIdAndDeletedAtIsNull(classId), HttpStatus.OK);
    }

    public ApiResponse<List<StudentModel>> getStudentsByDepartment(Long departmentId) {
        return new ApiResponse<>("Students by department", studentRepository.findAllByStudentClassDepartmentIdAndDeletedAtIsNull(departmentId), HttpStatus.OK);
    }

    public IsBlackListed isStudentBlackListed( CreateAndUpdateStudent requestBody) {
        Map<String, String> student = new HashMap<>();
        student.put("firstName", requestBody.firstName());
        student.put("lastName", requestBody.lastName());
        student.put("dateOfBirth", requestBody.dateOfBirth().toString());
        try {
            return webClient.post()
                    .uri("https://api.sokharithy.me/api/black-list-student")
                    .bodyValue(student)
                    .retrieve()
                    .bodyToMono(IsBlackListed.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
