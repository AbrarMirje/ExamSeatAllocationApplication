package com.codecrafter.repository;

import com.codecrafter.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISupervisorRepository extends JpaRepository<Supervisor,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM supervisor WHERE block_id=?")
    Supervisor findByBlockBlockId(Long blockId);

    Supervisor findByBlock_BlockId(Long blockId);


}
