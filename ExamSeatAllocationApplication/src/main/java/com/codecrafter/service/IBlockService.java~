package com.codecrafter.service;

import com.codecrafter.entity.Block;
import com.codecrafter.entity.dto.BlockDto;
import com.codecrafter.entity.dto.helper.BlockDtoHelper;

import java.util.List;

public interface IBlockService {

    Block addBlock(BlockDto blockDto);

    List<BlockDtoHelper> getAllBlocks();

    Integer getAllEmptySeats();

    List<BlockDtoHelper> getAvailableBlocks();

    String deleteBlock(Long blockId);

    Block updateBlock(BlockDto blockDto);
}
