package com.codecrafter.repository;

import com.codecrafter.entity.Block;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlockRepository extends JpaRepository<Block,Long> {

    @Query("SELECT SUM(b.capacity - SIZE(b.seatAllocations)) FROM block_table b")
    Integer getTotalEmptySeats();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE block_table SET is_supervisor_allocated = :isSupervisorAllocated WHERE block_id = :blockId")
    void updateIsSupervisorAllocated(@Param("isSupervisorAllocated") boolean isSupervisorAllocated,
                                     @Param("blockId") Long blockId);


    @Query("SELECT b FROM block_table b WHERE b.isSupervisorAllocated = false")
    List<Block> findAvailableBlocks();

    @Query(nativeQuery = true, value = "SELECT * FROM block_table WHERE seat_number=? AND block_number=?")
    Block getBlockBySeatNumberAndBlockNumber(String seatNumber, String blockNumber);
}

