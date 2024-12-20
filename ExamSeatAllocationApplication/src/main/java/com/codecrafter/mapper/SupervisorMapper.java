package com.codecrafter.mapper;

import com.codecrafter.entity.Supervisor;
import com.codecrafter.entity.dto.SupervisorDto;

import java.util.ArrayList;
import java.util.List;

public class SupervisorMapper {

    public static SupervisorDto mapToSupervisorDto(Supervisor supervisor, SupervisorDto supervisorDto){

        supervisorDto.setSupervisorId(supervisor.getSupervisorId());
        supervisorDto.setSuperVisorName(supervisor.getSuperVisorName());
        supervisorDto.setContactNumber(supervisor.getContactNumber());
        supervisorDto.setEmail(supervisor.getEmail());
        supervisorDto.setDepartName(supervisor.getDepartName());
        supervisorDto.setBlockAllocated(supervisor.isBlockAllocated());
        supervisorDto.setBlock(supervisor.getBlock());
        return supervisorDto;
    }

    public static List<Supervisor> mapToSupervisors(List<SupervisorDto> supervisorDtos){

        List<Supervisor> supervisors=new ArrayList<>();
        supervisorDtos.forEach(supervisorDto -> {
            Supervisor supervisor=new Supervisor();
            supervisor.setSuperVisorName(supervisorDto.getSuperVisorName());
            supervisor.setContactNumber(supervisorDto.getContactNumber());
            supervisor.setEmail(supervisorDto.getEmail());
            supervisor.setDepartName(supervisorDto.getDepartName());
            supervisor.setBlockAllocated(supervisorDto.isBlockAllocated());
            supervisor.setBlock(supervisorDto.getBlock());
            supervisors.add(supervisor);
        });

        return supervisors;
    }

    public static Supervisor mapToSupervisor(SupervisorDto supervisorDto,Supervisor supervisor){

            supervisor.setSuperVisorName(supervisorDto.getSuperVisorName());
            supervisor.setContactNumber(supervisorDto.getContactNumber());
            supervisor.setEmail(supervisorDto.getEmail());
            supervisor.setDepartName(supervisorDto.getDepartName());
            supervisor.setBlockAllocated(supervisorDto.isBlockAllocated());
            supervisor.setBlock(supervisorDto.getBlock());

        return supervisor;
    }


    public static Supervisor mapToSupervisor2(SupervisorDto supervisorDto,Supervisor supervisor){

        supervisor.setSupervisorId(supervisorDto.getSupervisorId());
        supervisor.setSuperVisorName(supervisorDto.getSuperVisorName());
        supervisor.setContactNumber(supervisorDto.getContactNumber());
        supervisor.setEmail(supervisorDto.getEmail());
        supervisor.setDepartName(supervisorDto.getDepartName());
        supervisor.setBlockAllocated(supervisorDto.isBlockAllocated());
        supervisor.setBlock(supervisorDto.getBlock());

        return supervisor;
    }
}
