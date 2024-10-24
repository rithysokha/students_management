package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateCourse;
import com.student.student_management.dto.Status;
import com.student.student_management.model.CourseModel;
import com.student.student_management.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public ApiResponse<List<CourseModel>> getAllCourses() {
        try{
            return new ApiResponse<>("All courses", courseRepository.findAllByDeletedAtIsNull(), HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<CourseModel> getCourseById(Long id) {
        try{
            Optional<CourseModel> course = courseRepository.findByIdAndDeletedAtIsNull(id);
            return course.map(courseModel -> new ApiResponse<>("Course with id " + id, courseModel, HttpStatus.OK, Status.SUCCESS)).orElseGet(() -> new ApiResponse<>("Course not found", null, HttpStatus.NOT_FOUND, Status.FAIL));
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<CourseModel> createNewCourse(@Valid CreateAndUpdateCourse body) {
        try{
            CourseModel courseModel = new CourseModel();
            courseModel.setCourseName(body.courseName());
            courseModel.setMaxCredit(body.maxCredit());
            var newCourse = courseRepository.save(courseModel);
            return new ApiResponse<>("Course created", newCourse, HttpStatus.CREATED, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<CourseModel> deleteCourseById(Long id) {
        try{
            ApiResponse<CourseModel> course = getCourseById(id);
            if (course.httpStatus()!= HttpStatus.OK)
                return course;
            var courseRes = course.data();
            courseRes.setDeletedAt(LocalDateTime.now());
            courseRepository.save(courseRes);
            return new ApiResponse<>("Course Deleted", null, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
    @Transactional
    public ApiResponse<CourseModel> updateCourseById(Long id, @Valid CreateAndUpdateCourse body) {
        try{
            ApiResponse<CourseModel> course = getCourseById(id);
            if (course.httpStatus()!= HttpStatus.OK)
                return course;
            var courseBody = course.data();
            courseBody.setCourseName(body.courseName());
            courseBody.setMaxCredit(body.maxCredit());
            var courseRes = courseRepository.save(courseBody);
            return new ApiResponse<>("Course updated", courseRes, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
}
