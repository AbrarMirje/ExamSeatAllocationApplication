package com.codecrafter.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlockDto {

    private Long blockId;
    private String blockNumber;
    private Integer capacity;
}
