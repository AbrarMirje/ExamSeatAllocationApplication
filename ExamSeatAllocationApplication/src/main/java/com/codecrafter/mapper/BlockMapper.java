package com.codecrafter.mapper;

import com.codecrafter.entity.Block;
import com.codecrafter.entity.dto.BlockDto;

public class BlockMapper {

    public static BlockDto mapToBlockDto(Block block, BlockDto blockDto){
        blockDto.setBlockId(block.getBlockId());
        blockDto.setBlockNumber(block.getBlockNumber());
        blockDto.setCapacity(block.getCapacity());

        return blockDto;
    }

    public static Block mapToBlock(BlockDto blockDto,Block block){
        block.setBlockId(blockDto.getBlockId());
        block.setBlockNumber(blockDto.getBlockNumber());
        block.setCapacity(blockDto.getCapacity());

        return block;
    }
}
