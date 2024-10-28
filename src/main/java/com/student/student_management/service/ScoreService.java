package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateScore;
import com.student.student_management.dto.Status;
import com.student.student_management.model.CourseModel;
import com.student.student_management.model.ScoreModel;
import com.student.student_management.model.StudentModel;
import com.student.student_management.repository.ScoreRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final StudentService studentService;
    private final CourseService courseService;
    private final ExcelService excelService;

    public ApiResponse<ScoreModel> createOneScore(@Valid CreateAndUpdateScore body) {
        CourseModel course = courseService.getCourseById(body.courseId()).data();
        StudentModel student = studentService.getOneStudentById(body.studentId()).data();
        try {
            ScoreModel score = new ScoreModel(body.score(), course, student);
            var scoreRes = scoreRepository.save(score);
            return new ApiResponse<>("Score created", scoreRes, HttpStatus.CREATED, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    @Transactional
    public ApiResponse<ScoreModel> updateOneScore(Long id, @Valid CreateAndUpdateScore body) {
        CourseModel course = courseService.getCourseById(body.courseId()).data();
        StudentModel student = studentService.getOneStudentById(body.studentId()).data();
        try {
            Optional<ScoreModel> scoreOptional = scoreRepository.findById(id);
            if(scoreOptional.isEmpty()){
                return new ApiResponse<>("Score not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
            }
            ScoreModel score = scoreOptional.get();
            score.setScore(body.score());
            score.setCourse(course);
            score.setStudent(student);
            var scoreRes = scoreRepository.save(score);
            return new ApiResponse<>("Score updated", scoreRes, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<List<ScoreModel>> createMultipleScore(InputStream inputStream) {
    try{
        List<ScoreModel> scoreModelList = excelService.createScores(inputStream);
        return new ApiResponse<>("Created multiple score", scoreModelList, HttpStatus.CREATED, Status.SUCCESS);
    } catch (Exception e) {
        return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
    }
    }
}
