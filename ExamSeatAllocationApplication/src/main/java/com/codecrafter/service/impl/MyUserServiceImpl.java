package com.codecrafter.service.impl;

import com.codecrafter.entity.MyUser;
import com.codecrafter.entity.dto.MyUserDto;
import com.codecrafter.mapper.MyUserMapper;
import com.codecrafter.repository.IMyUserRepository;
import com.codecrafter.service.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService {

    private final IMyUserRepository myUserRepository;

    @Override
    public MyUser registerUser(MyUser myUser) {
        return myUserRepository.save(myUser);
    }

    @Override
    public boolean isLogin(String email, String password) {
        MyUser user=myUserRepository.findById(1L).get();

        return (user.getEmail().equals(email)) && user.getPassword().equals(password);
    }
}
