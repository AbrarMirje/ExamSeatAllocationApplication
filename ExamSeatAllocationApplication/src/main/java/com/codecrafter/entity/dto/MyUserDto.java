package com.codecrafter.entity.dto;

import lombok.Data;

@Data
public class MyUserDto {
    private Long myUserId;
    private String email;
    private String password;
}
