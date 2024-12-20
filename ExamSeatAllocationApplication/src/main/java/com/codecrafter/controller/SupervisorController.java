package com.codecrafter.controller;

import com.codecrafter.entity.dto.SupervisorDto;
import com.codecrafter.service.ISupervisorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supervisor")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SupervisorController {

    private final ISupervisorService supervisorService;

    @PostMapping("/add-supervisor")
    public ResponseEntity<?> addSupervisor(@RequestBody List<SupervisorDto> supervisorDtos){

        supervisorDtos.forEach(s->{
            System.out.println(s.getBlock().getBlockId());
        });
        return ResponseEntity.ok(supervisorService.addSupervisor(supervisorDtos));
    }

    @GetMapping("/get-supervisors")
    public ResponseEntity<?> getSupervisors(){
        return ResponseEntity.ok(supervisorService.getAllSupervisors());
    }

    @PutMapping("/update-supervisor")
    public ResponseEntity<?> updateSupervisor(@RequestBody SupervisorDto supervisorDto){

        if(supervisorService.updateSupervisor(supervisorDto)) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can,t update");
        }
    }

    @DeleteMapping("/delete-supervisor")
    public ResponseEntity<?> deleteSupervisor(@RequestParam Long supervisorId){
        return ResponseEntity.ok(supervisorService.deleteSupervisor(supervisorId));
    }
}
