package com.codecrafter.service;

import com.codecrafter.entity.Course;
import com.codecrafter.entity.dto.CourseDto;

import java.util.List;

public interface ICourseService {

    Course addCourse(CourseDto courseDto);

    List<CourseDto> getAllCourses();

    Boolean deleteCourse(Long courseId);

    Boolean updateCourse(CourseDto courseDto);
}
