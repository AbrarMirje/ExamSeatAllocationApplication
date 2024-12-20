package com.codecrafter.service.impl;

import com.codecrafter.entity.Block;
import com.codecrafter.entity.SeatAllocation;
import com.codecrafter.entity.Supervisor;
import com.codecrafter.entity.dto.BlockDto;
import com.codecrafter.entity.dto.helper.BlockDtoHelper;
import com.codecrafter.entity.dto.helper.BlockSeatBlockNumber;
import com.codecrafter.mapper.BlockMapper;
import com.codecrafter.repository.IBlockRepository;
import com.codecrafter.repository.ISeatAllocationRepository;
import com.codecrafter.repository.IStudentRepository;
import com.codecrafter.repository.ISupervisorRepository;
import com.codecrafter.service.IBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements IBlockService {

    private final IBlockRepository blockRepository;
    private final ISeatAllocationRepository seatAllocationRepository;
    private final ISupervisorRepository supervisorRepository;
    private final IStudentRepository studentRepository;

    @Override
    public Block addBlock(BlockDto blockDto) {

        Block block=BlockMapper.mapToBlock(blockDto,new Block());
        return blockRepository.save(block);
    }

    @Override
    public List<BlockDtoHelper> getAllBlocks() {

        List<BlockDtoHelper> blocks=new ArrayList<>();
        blockRepository.findAll().forEach(block -> {
            BlockDtoHelper blockDtoHelper=new BlockDtoHelper();

            blockDtoHelper.setBlockId(block.getBlockId());
            blockDtoHelper.setBlockNumber(block.getBlockNumber());
            blockDtoHelper.setSeatAllocations(seatAllocationRepository.getAllByBlockBlockId(block.getBlockId()));
            blockDtoHelper.setCapacity(block.getCapacity());
            if (supervisorRepository.findByBlockBlockId(block.getBlockId()) != null){
                blockDtoHelper.setSupervisorName(supervisorRepository.findByBlockBlockId(block.getBlockId()).getSuperVisorName());
            } else {
                blockDtoHelper.setSupervisorName("Supervisor is not allocated.");
            }
            blocks.add(blockDtoHelper);

        });
        return blocks;
    }

    @Override
    public Integer getAllEmptySeats() {
        return blockRepository.getTotalEmptySeats();
    }

    @Override
    public List<BlockDtoHelper> getAvailableBlocks() {
        List<BlockDtoHelper> blocks=new ArrayList<>();
        blockRepository.findAvailableBlocks().forEach(block -> {
            BlockDtoHelper blockDtoHelper=new BlockDtoHelper();

            blockDtoHelper.setBlockId(block.getBlockId());
            blockDtoHelper.setBlockNumber(block.getBlockNumber());
            blockDtoHelper.setSeatAllocations(seatAllocationRepository.getAllByBlockBlockId(block.getBlockId()));
            blockDtoHelper.setCapacity(block.getCapacity());
            blocks.add(blockDtoHelper);

        });
        return blocks;
    }

    @Override
    public String deleteBlock(Long blockId) {


        Supervisor supervisor = supervisorRepository.findByBlock_BlockId(blockId);
        if (supervisor != null) {
            supervisor.setBlock(null);  // Unassign the block from supervisor
            supervisor.setBlockAllocated(false); // Update block allocated status
            supervisorRepository.save(supervisor);
        }
        List<SeatAllocation> seatAllocations=seatAllocationRepository.findByBlock_BlockId(blockId);

        seatAllocations.forEach(seatAllocation -> {
            Long studentId=seatAllocation.getStudent().getStudentId();
            studentRepository.updateSeatAllocationStatus(studentId,false);
        });
        blockRepository.deleteById(blockId);
        return "Block deleted successfully!";
    }

    @Override
    public Block updateBlock(BlockDto blockDto) {

        Block block=BlockMapper.mapToBlock(blockDto,new Block());

        Block existedBlock=blockRepository.findById(block.getBlockId()).get();

        existedBlock.setBlockNumber(blockDto.getBlockNumber());
        existedBlock.setCapacity(blockDto.getCapacity());
        existedBlock.setSupervisorAllocated(existedBlock.isSupervisorAllocated());
        return blockRepository.save(existedBlock);
    }

    @Override
    public Block getBlockBySeatNumberAndBlockNumber(BlockSeatBlockNumber blockSeatBlockNumber) {
        return blockRepository.getBlockBySeatNumberAndBlockNumber(blockSeatBlockNumber.getSeatNumber(),blockSeatBlockNumber.getBlockNumber());
    }
}
