package com.codecrafter.mapper;

import com.codecrafter.entity.MyUser;
import com.codecrafter.entity.dto.MyUserDto;

public class MyUserMapper {

    public static MyUserDto mapToMyUserDto(MyUser myUser, MyUserDto myUserDto){

        myUserDto.setMyUserId(myUser.getMyUserId());
        myUserDto.setEmail(myUser.getEmail());
        myUserDto.setPassword(myUser.getPassword());
        return myUserDto;
    }

    public static MyUser mapToMyUser(MyUserDto myUserDto,MyUser myUser){

        myUser.setMyUserId(myUserDto.getMyUserId());
        myUser.setEmail(myUserDto.getEmail());
        myUser.setPassword(myUserDto.getPassword());
        return myUser;
    }
}
