package com.codecrafter.controller;

import com.codecrafter.entity.dto.SeatAllocationDto;
import com.codecrafter.entity.dto.helper.StudentBlockSeatNumberDtoHelper;
import com.codecrafter.entity.dto.helper.StudentPositionDtoHelper;
import com.codecrafter.entity.dto.helper.StudentPrnMobDtoHelper;
import com.codecrafter.service.ISeatAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat-allocation")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SeatAllocationController {

    private final ISeatAllocationService seatAllocationService;

    @PostMapping("/allocate-seats")
    public ResponseEntity<?> allocateSeats(){
        seatAllocationService.allocateSeats();
        return ResponseEntity.ok("Seats are allocated");
    }

    @GetMapping("/get-allocated-students")
    public ResponseEntity<?> getAllocatedStudents(){

        System.out.println("SIZE: "+seatAllocationService.getAllSeatNumbers().size());
        return ResponseEntity.ok(seatAllocationService.getAllSeatNumbers());
    }

    @DeleteMapping("/clear-seat-allocation")
    public ResponseEntity<?> clearSeatAllocations(){
        return ResponseEntity.status(HttpStatus.OK).body(seatAllocationService.clearSeatAllocations());
    }

    @PostMapping("/get-student-hall-ticket")
    public ResponseEntity<?> getStudentHallTicket(@RequestBody StudentPrnMobDtoHelper studentPrnMobDtoHelper){
        System.out.println("Request Comming");
        return ResponseEntity.status(HttpStatus.OK).body(seatAllocationService.getStudentHallTicket(studentPrnMobDtoHelper));
    }

    @PostMapping("/get-student-by-block-seat-number")
    public ResponseEntity<SeatAllocationDto> getStudentByBlockSeatNumber(@RequestBody StudentBlockSeatNumberDtoHelper studentBlockSeatNumberDtoHelper){
           return ResponseEntity.status(HttpStatus.OK).body(seatAllocationService.getStudentByBlockSeatNumber(studentBlockSeatNumberDtoHelper));
    }

    @GetMapping("/get-block-by-seat-number")
    public ResponseEntity<?> getBlockBySeatNumber(@RequestParam String seatNumber){

        return ResponseEntity.ok(seatAllocationService.getBlockBySeatNumber(seatNumber));
    }

    @PostMapping("/get-student-position")
    public ResponseEntity<?> getStudentPosition(@RequestBody StudentPositionDtoHelper studentPositionDtoHelper){

     return ResponseEntity.ok(seatAllocationService.getStudentPosition(studentPositionDtoHelper));
    }

}
