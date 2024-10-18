package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateFaculty;
import com.student.student_management.dto.CreateMultipleFaculty;
import com.student.student_management.dto.Status;
import com.student.student_management.model.FacultyModel;
import com.student.student_management.repository.FacultyRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public ApiResponse<List<FacultyModel>> getAllFaculty() {
        try{
            return new ApiResponse<>("All faculties", facultyRepository.findAllByDeletedAtIsNull(), HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {

            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<FacultyModel> getOneFacultyById(Long id) {
        try{
            Optional<FacultyModel> facultyOptional = facultyRepository.findByIdAndDeletedAtIsNull(id);
            return facultyOptional.map(facultyModel -> new ApiResponse<>("Faculty with id " + id, facultyModel, HttpStatus.OK, Status.SUCCESS)).orElseGet(() -> new ApiResponse<>("Faculty not found", null, HttpStatus.NOT_FOUND, Status.FAIL));
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
    @Transactional
    public ApiResponse<FacultyModel> deleteFacultyById(Long id) {
        try{
            ApiResponse<FacultyModel> faculty = getOneFacultyById(id);
            if(!faculty.httpStatus().equals(HttpStatus.OK)){
                return faculty;
            }
            var facultyResponse = faculty.data();
            facultyResponse.setDeletedAt(LocalDateTime.now());
            facultyRepository.save(facultyResponse);
            return new ApiResponse<>("Faculty deleted", facultyResponse, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
    @Transactional
    public ApiResponse<List<Long>> deleteMultipleFaculty(List<Long> ids) {
        try {
            for (Long id : ids) {
                deleteFacultyById(id);
            }
            return new ApiResponse<>("Faculties deleted", ids, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<FacultyModel> createFaculty(@Valid CreateAndUpdateFaculty body) {
        try {
            FacultyModel facultyModel = new FacultyModel();
            facultyModel.setFacultyName(body.facultyName());
            facultyModel.setCreatedAt(LocalDateTime.now());
            var faculty = facultyRepository.save(facultyModel);
            return new ApiResponse<>("Faculty created", faculty, HttpStatus.CREATED, Status.SUCCESS);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<List<FacultyModel>> createMultipleFaculty(@Valid CreateMultipleFaculty body) {
        try {
            List<FacultyModel> createdFaculties = new ArrayList<>();
            for (CreateAndUpdateFaculty faculty : body.faculties()) {
                FacultyModel facultyModel = new FacultyModel();
                facultyModel.setFacultyName(faculty.facultyName());
                facultyModel.setCreatedAt(LocalDateTime.now());
                createdFaculties.add(facultyRepository.save(facultyModel));
            }
            return new ApiResponse<>("Faculties created", createdFaculties, HttpStatus.CREATED, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    @Transactional
    public ApiResponse<FacultyModel> updateFacultyById(Long id, @Valid CreateAndUpdateFaculty body) {
        try{
            ApiResponse<FacultyModel> faculty = getOneFacultyById(id);
            if(!faculty.httpStatus().equals(HttpStatus.OK)){
                return faculty;
            }
            faculty.data().setFacultyName(body.facultyName());
            var facultyResponse = facultyRepository.save(faculty.data());
            return new ApiResponse<>("Faculty updated", facultyResponse, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
}
