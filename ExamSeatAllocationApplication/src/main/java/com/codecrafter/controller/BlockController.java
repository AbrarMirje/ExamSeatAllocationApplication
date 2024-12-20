package com.codecrafter.controller;

import com.codecrafter.entity.dto.BlockDto;
import com.codecrafter.entity.dto.helper.BlockSeatBlockNumber;
import com.codecrafter.service.IBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BlockController {

    private final IBlockService blockService;

    @PostMapping("/add-block")
    public ResponseEntity<?> addBlock(@RequestBody BlockDto blockDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(blockService.addBlock(blockDto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-blocks")
    public ResponseEntity<?> getAllBlocks(){
        return ResponseEntity.ok(blockService.getAllBlocks());
    }

    @GetMapping("/get-total-empty-seats")
    public ResponseEntity<?> getEmptySeats(){
        return ResponseEntity.ok(blockService.getAllEmptySeats());
    }

    @GetMapping("/get-available-blocks")
    public ResponseEntity<?> getAvailableBlocks(){
        return ResponseEntity.ok(blockService.getAvailableBlocks());
    }

    @DeleteMapping("/delete-block")
    public ResponseEntity<?> deleteBlock(@RequestParam Long blockId){
      return ResponseEntity.ok(blockService.deleteBlock(blockId));
    }

    @PutMapping("/update-block")
    public ResponseEntity<?> updateBlock(@RequestBody BlockDto blockDto){

        System.out.println("update api");
        System.out.println(blockDto.getBlockId());
        return ResponseEntity.ok(blockService.updateBlock(blockDto));
    }

    @PostMapping("/get-block-by-seat-number-and-block")
    public ResponseEntity<?> getBlockBySeatNumberAndBlockNumber(@RequestBody BlockSeatBlockNumber blockSeatBlockNumber){

        return ResponseEntity.ok(blockService.getBlockBySeatNumberAndBlockNumber(blockSeatBlockNumber));
    }
}
