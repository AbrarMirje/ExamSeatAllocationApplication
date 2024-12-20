package com.codecrafter.service;

import com.codecrafter.entity.Student;
import com.codecrafter.entity.dto.SeatAllocationDto;
import com.codecrafter.entity.dto.StudentDto;
import com.codecrafter.entity.dto.helper.StudentDtoHelper;

import java.util.List;

public interface IStudentService {

    List<Student> addStudent(List<StudentDto> studentDtos);

    List<StudentDtoHelper> getAllStudents();

    List<StudentDtoHelper> getStudentsByCourse(Long courseId,String year);

}
