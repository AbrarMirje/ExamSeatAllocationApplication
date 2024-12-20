package com.codecrafter.controller;

import com.codecrafter.entity.Course;
import com.codecrafter.entity.Student;
import com.codecrafter.entity.dto.StudentDto;
import com.codecrafter.entity.dto.helper.CourseYearDtoHelper;
import com.codecrafter.mapper.StudentMapper;
import com.codecrafter.service.impl.StudentServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudentController {

    private final StudentServiceImpl studentService;

    @PostMapping("/add-student")
    public ResponseEntity<?> createStudent(@RequestBody List<StudentDto> studentDtos){

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(studentDtos));
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-students")
    public ResponseEntity<?> getAllStudents(){

        System.out.println("Total students: "+studentService.getAllStudents().size());
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping("/get-students-by-course")
    public ResponseEntity<?> getStudentsByCourse(@RequestBody CourseYearDtoHelper courseYearDtoHelper){
        return ResponseEntity.ok(studentService.getStudentsByCourse(courseYearDtoHelper.getCourseId(),courseYearDtoHelper.getYear()));
    }

}
