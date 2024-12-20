package com.codecrafter.service;

import com.codecrafter.entity.Block;
import com.codecrafter.entity.dto.SeatAllocationDto;
import com.codecrafter.entity.dto.helper.StudentBlockSeatNumberDtoHelper;
import com.codecrafter.entity.dto.helper.StudentPositionDtoHelper;
import com.codecrafter.entity.dto.helper.StudentPrnMobDtoHelper;

import java.util.List;

public interface ISeatAllocationService {

    void allocateSeats();

    List<SeatAllocationDto> getAllSeatNumbers();

    String clearSeatAllocations();

    SeatAllocationDto getStudentHallTicket(StudentPrnMobDtoHelper studentPrnMobDtoHelper);

    SeatAllocationDto getStudentByBlockSeatNumber(StudentBlockSeatNumberDtoHelper studentBlockSeatNumberDtoHelper);

    Block getBlockBySeatNumber(String seatNumber);

    Integer getStudentPosition(StudentPositionDtoHelper studentPositionDtoHelper);
}
