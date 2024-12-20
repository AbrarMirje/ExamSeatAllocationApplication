package com.codecrafter.service.impl;

import com.codecrafter.entity.Block;
import com.codecrafter.entity.Course;
import com.codecrafter.entity.SeatAllocation;
import com.codecrafter.entity.Student;
import com.codecrafter.entity.dto.StudentDto;
import com.codecrafter.entity.dto.helper.StudentDtoHelper;
import com.codecrafter.mapper.StudentMapper;
import com.codecrafter.repository.IBlockRepository;
import com.codecrafter.repository.ICourseRepository;
import com.codecrafter.repository.ISeatAllocationRepository;
import com.codecrafter.repository.IStudentRepository;
import com.codecrafter.service.ISeatAllocationService;
import com.codecrafter.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;

    private final   ICourseRepository courseRepository;

    private final ISeatAllocationRepository seatAllocationRepository;

    private final ISeatAllocationService seatAllocationService;
    private final IBlockRepository blockRepository;

    private static final Map<String, Integer> departmentSequenceMap = new HashMap<>();

    @Override
    public List<Student> addStudent(List<StudentDto> studentDtos) {

        List<Student> students = StudentMapper.mapToStudents(studentDtos);

        students.forEach(student -> {
            Course course=courseRepository.findById(student.getCourse().getCourseId()).get();

            if(student.getYear().equals("I")){
                student.setPrnNumber(StudentServiceImpl.generatePRN(course.getCourseName().toUpperCase(),"2024"));
            }else if(student.getYear().equals("II")){
                student.setPrnNumber(StudentServiceImpl.generatePRN(course.getCourseName().toUpperCase(),"2023"));
            }else if(student.getYear().equals("III")){
                student.setPrnNumber(StudentServiceImpl.generatePRN(course.getCourseName().toUpperCase(),"2022"));
            }else if(student.getYear().equals("IV")){
                student.setPrnNumber(StudentServiceImpl.generatePRN(course.getCourseName().toUpperCase(),"2021"));
            }else {
                student.setPrnNumber(StudentServiceImpl.generatePRN(course.getCourseName().toUpperCase(),"2020"));
            }

            studentRepository.save(student);
        });

        seatAllocationService.allocateSeats();
        return students;
    }

    @Override
    public List<StudentDtoHelper> getAllStudents() {

        List<StudentDtoHelper> students=new ArrayList<>();
        studentRepository.findAll().forEach(student -> {
            StudentDtoHelper studentDtoHelper=new StudentDtoHelper();

            studentDtoHelper.setStudentId(student.getStudentId());
            studentDtoHelper.setFirstName(student.getFirstName());
            studentDtoHelper.setLastName(student.getLastName());
            studentDtoHelper.setPrnNumber(student.getPrnNumber());
            studentDtoHelper.setCourseName(courseRepository.findById(student.getCourse().getCourseId()).get().getCourseName());
            studentDtoHelper.setSemester(student.getSemester());
            studentDtoHelper.setRollNumber(student.getRollNumber());
            studentDtoHelper.setGender(student.getGender());
            studentDtoHelper.setMobileNumber(student.getMobileNumber());
            studentDtoHelper.setSeatAllocated(student.isSeatAllocated());
            SeatAllocation sa=seatAllocationRepository.findByStudent(student).get();
            studentDtoHelper.setSeatNumber(sa.getSeatNumber());
            students.add(studentDtoHelper);
        });

        return students;
    }

    @Override
    public List<StudentDtoHelper> getStudentsByCourse(Long courseId,String year) {

        List<StudentDtoHelper> students=new ArrayList<>();
        studentRepository.findAllByCourseCourseIdYear(courseId,year).forEach(student -> {
            StudentDtoHelper studentDtoHelper=new StudentDtoHelper();

            studentDtoHelper.setStudentId(student.getStudentId());
            studentDtoHelper.setFirstName(student.getFirstName());
            studentDtoHelper.setLastName(student.getLastName());
            studentDtoHelper.setPrnNumber(student.getPrnNumber());
            studentDtoHelper.setCourseName(courseRepository.findById(student.getCourse().getCourseId()).get().getCourseName());
            studentDtoHelper.setSemester(student.getSemester());
            studentDtoHelper.setRollNumber(student.getRollNumber());
            studentDtoHelper.setGender(student.getGender());
            studentDtoHelper.setMobileNumber(student.getMobileNumber());
            studentDtoHelper.setSeatAllocated(student.isSeatAllocated());
            SeatAllocation sa=seatAllocationRepository.findByStudent(student).get();
            studentDtoHelper.setSeatNumber(sa.getSeatNumber());
            Block block = blockRepository.findById(sa.getBlock().getBlockId()).get();
            studentDtoHelper.setBlockNumber(block.getBlockNumber());
            students.add(studentDtoHelper);
        });

        return students;
    }

    public static String generatePRN(String departmentCode, String admissionYear) {
        int sequence = departmentSequenceMap.getOrDefault(departmentCode, 1);

        departmentSequenceMap.put(departmentCode, sequence + 1);

        return String.format("%s%s%04d", departmentCode, admissionYear, sequence);
    }

}
