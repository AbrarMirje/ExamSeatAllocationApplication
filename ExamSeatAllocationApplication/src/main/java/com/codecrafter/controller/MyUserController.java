package com.codecrafter.controller;

import com.codecrafter.entity.MyUser;
import com.codecrafter.entity.dto.MyUserDto;
import com.codecrafter.mapper.MyUserMapper;
import com.codecrafter.repository.IMyUserRepository;
import com.codecrafter.service.IMyUserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class MyUserController {

    @Autowired
    private IMyUserService userService;
    @Autowired
    private IMyUserRepository myUserRepository;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody MyUser myUser){
        return ResponseEntity.ok(userService.registerUser(myUser));
    }

    @GetMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestParam String email,@RequestParam String password){
        return ResponseEntity.ok(userService.isLogin(email,password));
    }

    @PostConstruct
    @Transactional
    public void createAdmin() {
        // Check if the admin user already exists
        MyUser existingAdmin = myUserRepository.findByEmail("admin@gmail.com");
        if (existingAdmin == null) {
            MyUser myUser = new MyUser();
//            myUser.setMyUserId(1L);
            myUser.setEmail("admin@gmail.com");
            myUser.setPassword("admin123");
            userService.registerUser(myUser);
        }
    }


}
