package com.codecrafter.service;


import com.codecrafter.entity.Supervisor;
import com.codecrafter.entity.dto.SupervisorDto;
import com.codecrafter.entity.dto.helper.SupervisorDtoHelper;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ISupervisorService {

    List<Supervisor> addSupervisor(List<SupervisorDto> supervisorDtos);

    List<SupervisorDtoHelper> getAllSupervisors();

    boolean updateSupervisor(SupervisorDto supervisorDto);

    String deleteSupervisor(Long supervisorId);
}
