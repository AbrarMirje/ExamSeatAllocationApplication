package com.codecrafter.repository;

import com.codecrafter.entity.Course;
import com.codecrafter.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByPrnNumberAndMobileNumber(String prnNumber,String mobileNumber);

    @Modifying
    @Transactional
    @Query("UPDATE student_table s SET s.isSeatAllocated = :isSeatAllocated WHERE s.studentId = :studentId")
    int updateSeatAllocationStatus(@Param("studentId") Long studentId, @Param("isSeatAllocated") boolean isSeatAllocated);

    List<Student> findAllByCourseOrderByRollNumberAsc(Course course);

    @Query(nativeQuery = true,value = "SELECT * FROM student_table WHERE course_id=? AND year=?")
    List<Student> findAllByCourseCourseIdYear(Long courseId,String year);

    List<Student> findByCourse_CourseId(Long courseId);

}
