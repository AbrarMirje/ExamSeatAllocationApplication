package com.codecrafter.service.impl;

import com.codecrafter.entity.Course;
import com.codecrafter.entity.dto.CourseDto;
import com.codecrafter.mapper.CourseMapper;
import com.codecrafter.repository.ICourseRepository;
import com.codecrafter.repository.IStudentRepository;
import com.codecrafter.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final ICourseRepository courseRepository;
    private final IStudentRepository studentRepository;

    @Override
    public Course addCourse(CourseDto courseDto) {

        return courseRepository.save(CourseMapper.mapToCourse(courseDto,new Course()));
    }

    @Override
    public List<CourseDto> getAllCourses() {

        List<CourseDto> courses=new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            CourseDto courseDto=new CourseDto();
            courseDto.setCourseId(course.getCourseId());
            courseDto.setCourseName(course.getCourseName());
            courses.add(courseDto);
        });

        return courses;
    }

    @Override
    public Boolean deleteCourse(Long courseId) {
       if (studentRepository.findByCourse_CourseId(courseId).isEmpty()){
           courseRepository.deleteById(courseId);
           return true;
       } else {
           return false;
       }
    }

    @Override
    public Boolean updateCourse(CourseDto courseDto) {
        if (studentRepository.findByCourse_CourseId(courseDto.getCourseId()).isEmpty()) {
            Course course = CourseMapper.mapToCourse(courseDto, new Course());
            Course existedCourse = courseRepository.findById(course.getCourseId()).get();
            existedCourse.setCourseName(course.getCourseName());
            courseRepository.save(existedCourse);
            return true;
        } else {
            return false;
        }
    }
}
