package com.codecrafter.mapper;

import com.codecrafter.entity.Course;
import com.codecrafter.entity.dto.CourseDto;

public class CourseMapper {

    public static CourseDto mapToCourseDto(Course course,CourseDto courseDto){

        courseDto.setCourseId(course.getCourseId());
        courseDto.setCourseName(course.getCourseName());
//        courseDto.setDepartment(course.getDepartment());
        return courseDto;
    }

    public static Course mapToCourse(CourseDto courseDto,Course course){
        course.setCourseId(courseDto.getCourseId());
        course.setCourseName(courseDto.getCourseName());
//        course.setDepartment(courseDto.getDepartment());
        return course;
    }
}
