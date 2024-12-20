package com.codecrafter.service.impl;

import com.codecrafter.entity.Block;
import com.codecrafter.entity.Course;
import com.codecrafter.entity.SeatAllocation;
import com.codecrafter.entity.Student;
import com.codecrafter.entity.dto.SeatAllocationDto;
import com.codecrafter.entity.dto.helper.StudentBlockSeatNumberDtoHelper;
import com.codecrafter.entity.dto.helper.StudentPositionDtoHelper;
import com.codecrafter.entity.dto.helper.StudentPrnMobDtoHelper;
import com.codecrafter.exception.ResourceNotFoundException;
import com.codecrafter.repository.*;
import com.codecrafter.service.ISeatAllocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatAllocationServiceImpl implements ISeatAllocationService {

    private final IStudentRepository studentRepository;

    private final IBlockRepository blockRepository;

    private final ISeatAllocationRepository seatAllocationRepository;

    private final ICourseRepository courseRepository;

    private final ISupervisorRepository supervisorRepository;

    @Transactional
    @Override
    public void allocateSeats() {
        List<Course> courses = courseRepository.findAll();
        List<Block> blocks = blockRepository.findAll();

        if (blocks.isEmpty()) {
            throw new RuntimeException("No blocks available for allocation.");
        }

        // Fetch the last allocated seat details
        SeatAllocation lastAllocation = seatAllocationRepository.findTopByOrderByIdDesc();
        int blockIndex = 0;
        int seatNumber = 1;

        // If there is a last allocation, continue from there
        if (lastAllocation != null) {
            Block lastBlock = lastAllocation.getBlock();
            blockIndex = blocks.indexOf(lastBlock);

            // Extract seat number from last allocation (if available) and increment it
            seatNumber = extractSeatNumber(lastAllocation.getSeatNumber()) + 1;

            // If the last block was full, move to the next block
            if (seatNumber > 30) {
                blockIndex++;
                seatNumber = 1;  // Reset seat number for the next block
            }
        }

        // Iterate through each course and its students
        for (Course course : courses) {
            List<Student> students = studentRepository.findAllByCourseOrderByRollNumberAsc(course);

            for (Student student : students) {
                if (seatAllocationRepository.existsByStudent(student)) {
                    continue; // Skip if student is already allocated
                }

                // Allocate seat to the student
                while (blockIndex < blocks.size()) {
                    Block currentBlock = blocks.get(blockIndex);

                    // Check if the current block is full
                    if (seatNumber > currentBlock.getCapacity()) {
                        blockIndex++;  // Move to the next block
                        seatNumber = 1;  // Reset seat number for the new block
                        continue;  // Continue to the next block
                    }

                    // Generate formatted seat number: CourseName + SeatNumber (e.g., BCA001)
                    String formattedSeatNumber = generateFormattedSeatNumber(course.getCourseName(), seatNumber);

                    // Allocate the seat to the student
                    SeatAllocation allocation = new SeatAllocation();
                    allocation.setBlock(currentBlock);
                    allocation.setSeatNumber(formattedSeatNumber);
                    allocation.setStudent(student);

                    seatAllocationRepository.save(allocation);
                    studentRepository.updateSeatAllocationStatus(student.getStudentId(), true);
                    seatNumber++;  // Increment seat number for the next student
                    break;  // Exit the loop once the seat is allocated
                }

                // If no blocks are available, throw an exception
                if (blockIndex >= blocks.size()) {
                    throw new RuntimeException("Not enough blocks available to allocate seats for all students.");
                }
            }
        }
    }

    private String generateFormattedSeatNumber(String courseName, int seatNumber) {
        // Remove spaces and take the first 3 letters of the course name (uppercase) + padded seat number
        String coursePrefix = courseName.replaceAll("\\s+", "").toUpperCase();
        return String.format("%s%03d", coursePrefix, seatNumber);
    }

    private int extractSeatNumber(String seatNumber) {
        // Extract numeric part of seat number (e.g., BCA030 → 30)
        if (seatNumber == null || seatNumber.isEmpty()) {
            return 0; // Default to 0 if seat number is invalid
        }
        return Integer.parseInt(seatNumber.replaceAll("\\D+", "")); // Remove non-digits and parse to int
    }



    @Override
    public List<SeatAllocationDto> getAllSeatNumbers() {
        List<SeatAllocationDto> seatAllocationDtos=new ArrayList<>();

        seatAllocationRepository.findAll().forEach(seatAllocation -> {

            SeatAllocationDto seatAllocationDto=new SeatAllocationDto();

            seatAllocationDto.setId(seatAllocation.getId());

            Student student=studentRepository.findById(seatAllocation.getStudent().getStudentId()).get();
            seatAllocationDto.setFirstName(student.getFirstName());
            seatAllocationDto.setLastName(student.getLastName());
            seatAllocationDto.setPrnNumber(student.getPrnNumber());
            seatAllocationDto.setCourseName(student.getCourse().getCourseName());
            seatAllocationDto.setSemester(student.getSemester());
            seatAllocationDto.setGender(student.getGender());
            seatAllocationDto.setRollNumber(student.getRollNumber());
            seatAllocationDto.setMobileNumber(student.getMobileNumber());

            Block block=blockRepository.findById(seatAllocation.getBlock().getBlockId()).get();
            seatAllocationDto.setBlockNumber(block.getBlockNumber());

            seatAllocationDto.setSeatNumber(seatAllocation.getSeatNumber());
            seatAllocationDtos.add(seatAllocationDto);
        });

        return seatAllocationDtos;
    }

    @Override
    @Transactional
    public String clearSeatAllocations() {
        seatAllocationRepository.findAll().forEach(seatAllocation -> {
            studentRepository.updateSeatAllocationStatus(seatAllocation.getStudent().getStudentId(), false);

            Block block = seatAllocation.getBlock();
            System.out.println("Block => " + block.getBlockId());


        });
        blockRepository.findAll().forEach(block -> {
            blockRepository.updateIsSupervisorAllocated(false, block.getBlockId());
        });
        seatAllocationRepository.deleteAll();
        supervisorRepository.deleteAll();

        return "Seat allocation cleared successfully..!";
    }


    @Override
    public SeatAllocationDto getStudentHallTicket(StudentPrnMobDtoHelper studentPrnMobDtoHelper) {

        Student student=studentRepository.findByPrnNumberAndMobileNumber(studentPrnMobDtoHelper.getPrnNumber(),studentPrnMobDtoHelper.getMobileNumber())
                .orElseThrow(
                        ()->new ResourceNotFoundException("Student is not present with this PRN number and mobile number!"));

        SeatAllocationDto seatAllocationDto=new SeatAllocationDto();

        seatAllocationDto.setFirstName(student.getFirstName());
        seatAllocationDto.setLastName(student.getLastName());
        seatAllocationDto.setPrnNumber(student.getPrnNumber());
        seatAllocationDto.setCourseName(student.getCourse().getCourseName());
        seatAllocationDto.setSemester(student.getSemester());
        seatAllocationDto.setGender(student.getGender());
        seatAllocationDto.setRollNumber(student.getRollNumber());
        seatAllocationDto.setMobileNumber(student.getMobileNumber());


        SeatAllocation seatAllocation=seatAllocationRepository.findByStudent(student).get();
        Block block=blockRepository.findById(seatAllocation.getBlock().getBlockId()).get();
        seatAllocationDto.setBlockNumber(block.getBlockNumber());
        seatAllocationDto.setBlockId(block.getBlockId());
        seatAllocationDto.setSeatNumber(seatAllocation.getSeatNumber());
        seatAllocationDto.setId(seatAllocation.getId());

        return seatAllocationDto;

    }

    @Override
    public SeatAllocationDto getStudentByBlockSeatNumber(StudentBlockSeatNumberDtoHelper studentBlockSeatNumberDtoHelper) {

        SeatAllocation seatAllocation=seatAllocationRepository.findByBlock_BlockIdAndSeatNumber(studentBlockSeatNumberDtoHelper.getBlockId(),studentBlockSeatNumberDtoHelper.getSeatNumber());

        Student student=studentRepository.findById(seatAllocation.getStudent().getStudentId())
                .orElseThrow(
                        ()->new ResourceNotFoundException("Student is not present with this PRN number and mobile number!"));


        SeatAllocationDto seatAllocationDto=new SeatAllocationDto();

        seatAllocationDto.setFirstName(student.getFirstName());
        seatAllocationDto.setLastName(student.getLastName());
        seatAllocationDto.setPrnNumber(student.getPrnNumber());
        seatAllocationDto.setCourseName(student.getCourse().getCourseName());
        seatAllocationDto.setSemester(student.getSemester());
        seatAllocationDto.setGender(student.getGender());
        seatAllocationDto.setRollNumber(student.getRollNumber());
        seatAllocationDto.setMobileNumber(student.getMobileNumber());


        SeatAllocation sa=seatAllocationRepository.findByStudent(student).get();
        Block block=blockRepository.findById(sa.getBlock().getBlockId()).get();
        seatAllocationDto.setBlockNumber(block.getBlockNumber());

        seatAllocationDto.setSeatNumber(seatAllocation.getSeatNumber());
        seatAllocationDto.setId(seatAllocation.getId());

        return seatAllocationDto;
    }

    @Override
    public Block getBlockBySeatNumber(String seatNumber) {
        SeatAllocation seatAllocation=seatAllocationRepository.findBySeatNumber(seatNumber);
        return blockRepository.findById(seatAllocation.getBlock().getBlockId()).get();
    }

    @Override
    public Integer getStudentPosition(StudentPositionDtoHelper studentPositionDtoHelper) {

        List<SeatAllocation> seatAllocations = seatAllocationRepository.findByBlock_BlockId(studentPositionDtoHelper.getBlockId());

        seatAllocations.forEach(seatAllocation -> {
            System.out.println(seatAllocations.size());
        });
        Integer position=0;

        for(SeatAllocation s:seatAllocations){
            position++;
            if(s.getSeatNumber().equals(studentPositionDtoHelper.getSeatNumber())){
                break;
            }
        }

        System.out.println("Position: "+position);
        return position;
    }
}
