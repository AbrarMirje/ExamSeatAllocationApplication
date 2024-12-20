package com.codecrafter.entity.dto.helper;

import com.codecrafter.entity.SeatAllocation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BlockDtoHelper {

    private Long blockId;
    private String blockNumber;
    private Integer capacity;
    private String supervisorName;
    private List<SeatAllocation> seatAllocations;
}
