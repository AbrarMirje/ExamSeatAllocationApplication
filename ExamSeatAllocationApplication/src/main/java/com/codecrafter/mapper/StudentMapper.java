package com.codecrafter.mapper;

import com.codecrafter.entity.Student;
import com.codecrafter.entity.dto.StudentDto;
import com.codecrafter.entity.dto.helper.StudentDtoHelper;
import com.codecrafter.repository.ICourseRepository;
import com.codecrafter.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentMapper {

    public static StudentDto mapToStudentDto(Student student,StudentDto studentDto){
        studentDto.setStudentId(student.getStudentId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setGender(student.getGender());
        studentDto.setCourse(student.getCourse());
        studentDto.setSemester(student.getSemester());
        studentDto.setPrnNumber(student.getPrnNumber());
        studentDto.setRollNumber(student.getRollNumber());
        studentDto.setMobileNumber(student.getMobileNumber());
        studentDto.setYear(student.getYear());
        return studentDto;
    }

    public static Student mapToStudent(StudentDto studentDto,Student student){
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setGender(studentDto.getGender());
        student.setCourse(studentDto.getCourse());
        student.setSemester(studentDto.getSemester());
        student.setRollNumber(studentDto.getRollNumber());
        student.setMobileNumber(studentDto.getMobileNumber());
        student.setYear(studentDto.getYear());
        return student;
    }

    public static List<Student> mapToStudents(List<StudentDto> studentDtos){

        List<Student> students=new ArrayList<>();
        studentDtos.forEach(studentDto -> {

            Student student=new Student();
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());
            student.setGender(studentDto.getGender());
            student.setCourse(studentDto.getCourse());
            student.setSemester(studentDto.getSemester());
            student.setRollNumber(studentDto.getRollNumber());
            student.setMobileNumber(studentDto.getMobileNumber());
            student.setYear(studentDto.getYear());
            students.add(student);
        });
        return students;
    }

}
