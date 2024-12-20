package com.codecrafter.entity.dto.helper;

import com.codecrafter.entity.Block;
import lombok.Data;

@Data
public class SupervisorDtoHelper {
    private Long supervisorId;
    private String superVisorName;
    private String contactNumber;
    private String email;
    private String departName;
    private boolean isBlockAllocated;
    private String block;
}
