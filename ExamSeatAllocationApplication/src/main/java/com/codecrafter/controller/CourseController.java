package com.codecrafter.controller;

import com.codecrafter.entity.dto.CourseDto;
import com.codecrafter.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CourseController {

    private final ICourseService courseService;

    @PostMapping("/add-course")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-courses")
    public ResponseEntity<?> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @DeleteMapping("/delete-course")
    public ResponseEntity<?> deleteCourses(@RequestParam Long courseId){

        if(courseService.deleteCourse(courseId)){
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course not deleted!");
        }

    }

    @PutMapping("/update-course")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto){

        return ResponseEntity.ok(courseService.updateCourse(courseDto));
    }


}
