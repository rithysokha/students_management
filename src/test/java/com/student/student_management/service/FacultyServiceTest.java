package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateFaculty;
import com.student.student_management.dto.CreateMultipleFaculty;
import com.student.student_management.model.FacultyModel;
import com.student.student_management.repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FacultyServiceTest {
    @Mock
    FacultyRepository facultyRepository;

    @InjectMocks
    FacultyService facultyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFaculty() {
        when(facultyRepository.findAllByDeletedAtIsNull()).thenReturn(Collections.emptyList());
        ApiResponse<?> response = facultyService.getAllFaculty();
        assertEquals(HttpStatus.OK, response.httpStatus());
        assertEquals("All faculties", response.message());
    }

    @Test
    void getOneFacultyById() {
        FacultyModel faculty = new FacultyModel();
        when(facultyRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(faculty));
        ApiResponse<?> response = facultyService.getOneFacultyById(1L);
        assertEquals(HttpStatus.OK, response.httpStatus());
        assertEquals("Faculty with id 1", response.message());
    }

    @Test
    void deleteFacultyById() {
        FacultyModel faculty = new FacultyModel();
        when(facultyRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(faculty));
        ApiResponse<?> response = facultyService.deleteFacultyById(1L);
        assertEquals(HttpStatus.OK, response.httpStatus());
        assertEquals("Faculty deleted", response.message());
    }

    @Test
    void deleteMultipleFaculty() {
        FacultyModel faculty = new FacultyModel();
        when(facultyRepository.findByIdAndDeletedAtIsNull(any(Long.class))).thenReturn(Optional.of(faculty));
        ApiResponse<?> response = facultyService.deleteMultipleFaculty(Collections.singletonList(1L));
        assertEquals(HttpStatus.OK, response.httpStatus());
        assertEquals("Faculties deleted", response.message());
    }

    @Test
    void createFaculty() {
        FacultyModel faculty = new FacultyModel();
        when(facultyRepository.save(any(FacultyModel.class))).thenReturn(faculty);
        CreateAndUpdateFaculty body = new CreateAndUpdateFaculty("Faculty Name");
        ApiResponse<?> response = facultyService.createFaculty(body);
        assertEquals(HttpStatus.CREATED, response.httpStatus());
        assertEquals("Faculty created", response.message());
    }

    @Test
    void createMultipleFaculty() {
        FacultyModel faculty = new FacultyModel();
        when(facultyRepository.save(any(FacultyModel.class))).thenReturn(faculty);
        CreateAndUpdateFaculty body = new CreateAndUpdateFaculty("Faculty Name");
        ApiResponse<?> response = facultyService.createMultipleFaculty(new CreateMultipleFaculty(Collections.singletonList(body)));
        assertEquals(HttpStatus.CREATED, response.httpStatus());
        assertEquals("Faculties created", response.message());
    }

    @Test
    void updateFacultyById() {
        FacultyModel faculty = new FacultyModel();
        when(facultyRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(FacultyModel.class))).thenReturn(faculty);
        CreateAndUpdateFaculty body = new CreateAndUpdateFaculty("Updated Faculty Name");
        ApiResponse<?> response = facultyService.updateFacultyById(1L, body);
        assertEquals(HttpStatus.OK, response.httpStatus());
        assertEquals("Faculty updated", response.message());
    }
}