package com.codecrafter.entity.dto;

import com.codecrafter.entity.Block;
import lombok.Data;

@Data
public class SupervisorDto {
    private Long supervisorId;
    private String superVisorName;
    private String contactNumber;
    private String email;
    private String departName;
    private boolean isBlockAllocated;
    private Block block;
}
