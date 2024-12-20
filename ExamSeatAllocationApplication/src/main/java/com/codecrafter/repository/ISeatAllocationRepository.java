package com.codecrafter.repository;

import com.codecrafter.entity.SeatAllocation;
import com.codecrafter.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISeatAllocationRepository extends JpaRepository<SeatAllocation,Long> {

    boolean existsByStudent(Student student);

    List<SeatAllocation> getAllByBlockBlockId(Long blockId);

    Optional<SeatAllocation> findByStudent(Student student);

    SeatAllocation findTopByOrderByIdDesc();

    SeatAllocation findByBlock_BlockIdAndSeatNumber(Long blockId,String seatNumber);

    List<SeatAllocation> findByBlock_BlockId(Long blockId);

    SeatAllocation findBySeatNumber(String seatNumber);
}
