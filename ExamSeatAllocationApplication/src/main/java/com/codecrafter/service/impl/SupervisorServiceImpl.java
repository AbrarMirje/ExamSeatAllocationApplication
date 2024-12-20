package com.codecrafter.service.impl;

import com.codecrafter.entity.Supervisor;
import com.codecrafter.entity.dto.SupervisorDto;
import com.codecrafter.entity.dto.helper.SupervisorDtoHelper;
import com.codecrafter.exception.ResourceNotFoundException;
import com.codecrafter.mapper.SupervisorMapper;
import com.codecrafter.repository.IBlockRepository;
import com.codecrafter.repository.ISupervisorRepository;
import com.codecrafter.service.ISupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupervisorServiceImpl implements ISupervisorService {

    private final ISupervisorRepository supervisorRepository;
    private final IBlockRepository blockRepository;

    @Override
    public List<Supervisor> addSupervisor(List<SupervisorDto> supervisorDtos) {

        supervisorDtos.forEach(supervisorDto -> {
            Supervisor supervisor = SupervisorMapper.mapToSupervisor(supervisorDto, new Supervisor());
            supervisorRepository.save(supervisor);
            blockRepository.updateIsSupervisorAllocated(true, supervisorDto.getBlock().getBlockId());
        });
        return SupervisorMapper.mapToSupervisors(supervisorDtos);
    }

    @Override
    public List<SupervisorDtoHelper> getAllSupervisors() {

        List<SupervisorDtoHelper> supervisorDtos=new ArrayList<>();
        supervisorRepository.findAll().forEach(supervisor -> {

            SupervisorDtoHelper supervisorDtoHelper=new SupervisorDtoHelper();
            supervisorDtoHelper.setSupervisorId(supervisor.getSupervisorId());
            supervisorDtoHelper.setSuperVisorName(supervisor.getSuperVisorName());
            supervisorDtoHelper.setDepartName(supervisor.getDepartName());
            supervisorDtoHelper.setContactNumber(supervisor.getContactNumber());
            supervisorDtoHelper.setBlockAllocated(supervisor.isBlockAllocated());
            supervisorDtoHelper.setEmail(supervisor.getEmail());
            if(supervisor.getBlock()!=null) {
                supervisorDtoHelper.setBlock(blockRepository.findById(supervisor.getBlock().getBlockId()).get().getBlockNumber());
            }else{
                supervisorDtoHelper.setBlock("Please add block to supervisor");
            }
            supervisorDtos.add(supervisorDtoHelper);
        });

        return supervisorDtos;
    }

    @Override
    public boolean updateSupervisor(SupervisorDto supervisorDto) {

        boolean isUpdated=false;

        Supervisor supervisor=SupervisorMapper.mapToSupervisor2(supervisorDto,new Supervisor());

        Supervisor existedSupervisor=supervisorRepository.findById(supervisor.getSupervisorId()).orElseThrow(
                ()->new ResourceNotFoundException("Supervisor not found"));

        existedSupervisor.setSuperVisorName(supervisor.getSuperVisorName());
        existedSupervisor.setContactNumber(supervisor.getContactNumber());
        existedSupervisor.setDepartName(supervisor.getDepartName());
        existedSupervisor.setEmail(supervisor.getEmail());
        existedSupervisor.setBlockAllocated(existedSupervisor.isBlockAllocated());
        if (supervisor.getBlock() != null){

            blockRepository.updateIsSupervisorAllocated(true,supervisor.getBlock().getBlockId());
            blockRepository.updateIsSupervisorAllocated(false,existedSupervisor.getBlock().getBlockId());
            existedSupervisor.setBlock(supervisor.getBlock());
        } else {
            existedSupervisor.setBlock(existedSupervisor.getBlock());
            blockRepository.updateIsSupervisorAllocated(true,existedSupervisor.getBlock().getBlockId());
        }
        supervisorRepository.save(existedSupervisor);
        isUpdated=true;
        return isUpdated;
    }

    @Override
    public String deleteSupervisor(Long supervisorId) {

        Optional<Supervisor> supervisorOptional = supervisorRepository.findById(supervisorId);


        blockRepository.updateIsSupervisorAllocated(false,supervisorOptional.get().getBlock().getBlockId());

        Supervisor supervisor = supervisorOptional.get();
        supervisor.setBlock(null);

        supervisorRepository.deleteById(supervisorId);
        return "Supervisor deleted!";
    }
}